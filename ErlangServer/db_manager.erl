-module(db_manager).
-include("db_structure.hrl").

-export([init/0, start_db/0, stop_db/0, insert_message/2, read_messages/1, delete_message/1]).

% Support function to create database and tables
init() ->
  mnesia:create_schema([node()]),
  mnesia:start(),
  mnesia:change_table_copy_type(schema, node(), disc_copies),
  mnesia:create_table(messages, [{attributes, record_info(fields, messages)}, {type, ordered_set}, {disc_copies, [node()]}]),
  mnesia:create_table(ids, [{attributes, record_info(fields, ids)}, {type, set}, {disc_copies, [node()]}]).

% Starts mnesia database and creates a new process to delete periodically old messages
start_db() ->
  DataExipirationDelay = 24 * 60 * 60, % Number of seconds after which a message has to be deleted from the table
  GarbageCollectorSleepTime = 24 * 60 * 60 * 1000, % Number of milliseconds after which the garbage collector has to search old messages
  mnesia:start(),
  GarbageCollector = spawn(fun() -> garbage_collector_loop(GarbageCollectorSleepTime, DataExipirationDelay) end),
  ets:new(utilities, [set, named_table]),
  ets:insert(utilities, {garbage_collector, GarbageCollector}).

% Stops mnesia database and terminated the garbage collector process
stop_db() ->
  mnesia:stop(),
  [[GarbageCollector]] = ets:match(utilities, {garbage_collector, '$1'}),
  GarbageCollector ! stop,
  ets:delete(utilities).

% Inserts a new message
insert_message(Username, Message) ->
  Time = calendar:datetime_to_gregorian_seconds(calendar:local_time()),
  Id = mnesia:dirty_update_counter(ids, messages, 1),
  Record = #messages{id = Id, username = Username, time = Time, message = Message},
  Fun = fun() ->
          mnesia:write(Record)
        end,
  case mnesia:transaction(Fun) of
    {atomic, ok} -> success;
    _ -> error
  end.

% Extracts messages from a list
extract_messages([]) ->
  [];
extract_messages([H | T]) ->
  {messages,Id,Username,Time,Message} = H,
  [{Id,Username, calendar:gregorian_seconds_to_datetime(Time), Message} | extract_messages(T)].

% Gets the key that is stored before the one passed as argument
get_prev_key(0, Key) ->
  Key;
get_prev_key(Limit, Key) ->
  case mnesia:prev(messages, Key) of
    '$end_of_table' -> Key;
    PrevKey -> get_prev_key(Limit - 1, PrevKey)
  end
.

% Gets the first and the last keys to read that are the most recent ones
get_last_keys(Limit) ->
  case mnesia:last(messages) of
    '$end_of_table' -> '$end_of_table';
    LastKey -> {get_prev_key(Limit - 1, LastKey), LastKey}
  end.

% Reads messages. Limit specifies the maximum number of messages to read
read_messages(Limit) ->
  Fun =
    fun() ->
      case get_last_keys(Limit) of
        '$end_of_table' -> [];
        {FirstKey, LastKey} ->
          Query = #messages{id = '$1', username = '$2', time = '$3', message = '$4'},
          mnesia:select(messages,[{Query, [{'>', '$1', FirstKey - 1}, {'<', '$1', LastKey + 1}], ['$_']}])
      end
    end,
  case mnesia:transaction(Fun) of
    {atomic, Result} -> extract_messages(Result);
    _ -> io:format("Error reading messages~n"),
      error
  end.

% Searches messages older than the number of seconds specified by ExpirationDelay
select_old_messages(ExipirationDelay) ->
  Fun = fun() ->
          Threashold = calendar:datetime_to_gregorian_seconds(calendar:local_time()) - ExipirationDelay,
          Query = #messages{id = '$1', username = '$2', time = '$3', message = '$4'},
          Guard = {'<', '$3', Threashold},
          mnesia:select(messages, [{Query, [Guard], ['$1']}])
        end,
  case mnesia:transaction(Fun) of
    {atomic, Result} -> Result;
    _ -> io:format("Error selecting old messages~n"),
      []
  end.

% Deletes a message by id
delete_message(Id) ->
  Fun = fun() ->
          mnesia:delete({messages, Id})
        end,
  case mnesia:transaction(Fun) of
    {atomic, ok} -> success;
    _ -> io:format("Error deleting a message with id: ~p~n", [Id]),
      error
  end.

% Deletes all the messages in the argument list
delete_multiple_messages(ListIds) ->
  Fun =
    fun() ->
      Delete = fun(Id) -> mnesia:delete({messages, Id}) end,
      lists:foreach(Delete, ListIds)
    end,
  case mnesia:transaction(Fun) of
    {atomic, ok} -> success;
    _ -> io:format("Error deleting a messages"),
      error
  end.

% Deletes old messages
delete_old_messages(ExpirationDelay) ->
  delete_multiple_messages(select_old_messages(ExpirationDelay)).

% Garbage collector sequence of actions
garbage_collector_loop(SleepTime, ExpirationDelay) ->
  receive
    stop -> io:format("Garbage collector terminated~n")
  after SleepTime ->
    delete_old_messages(ExpirationDelay),
    garbage_collector_loop(SleepTime, ExpirationDelay)
  end.
