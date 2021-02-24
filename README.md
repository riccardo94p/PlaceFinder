# PlaceFinder
PlaceFinder is a distributed system aimed at reserving seats in University classrooms. There are also functionalities related to notification of students eventually positive to COVID-19 (anonymously) and to the possibility of reducing the available number of seats for social distancing.

PlaceFinder is built as a Spring Web Application that communicates with a remote Database with concurrent transaction management. The Database node uses JPA to facilitate the data access layer.
There is also a remote Erlang node which makes use of Mnesia DB to manage a simple bulletin board in which users can place public messages.
