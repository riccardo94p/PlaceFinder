-module(board_server).
-import(db_manager, [start_db/0, stop_db/0, insert_message/2, read_messages/1, delete_message/1]).

-export([init/1, handle_call/3, handle_cast/2, start_server/0, stop_server/0]).
-behavior(gen_server).


start_server() ->
  start_db(),
  gen_server:start({local, board_server}, ?MODULE, [], []).

stop_server() ->
  stop_db(),
  gen_server:stop(board_server).

init(_Args) ->
  {ok, {}}.

% Function declaration: handle_call(Request, From, State)
handle_call(Request, _, _) ->
  case Request of
    {insert, Username, Message} ->
      Response = insert_message(Username, Message),
      {reply, Response, {}};
    {read, Limit} ->
      Response = read_messages(Limit),
      {reply, Response, {}};
    {delete, Id} ->
      Response = delete_message(Id),
      {reply, Response, {}};
    _ ->
      {reply, bad_request, {}}
  end.

handle_cast(_, _) ->
  not_implemented.
