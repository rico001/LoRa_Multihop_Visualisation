# Protocol Messages
This document describes protocol messages used in the neighbour discovery process.

## Message definition
A message consists of the following:

* name
* set of params
* type(Broadcast, Multicast, Unicast)
* resulting actions, when proceeding such a message


## Declared Messages

| Name  | Params | Type | Resulting Actions | Example|
|---| --- | --- | --- |  --- | 
| JOIN  | source address, latitude, longitude| Broadcast | Replying with JOIN_REPLY message| ```JOIN;12DE;52.342;43.242;```
| JOIN_REPLY | source address, latitude, longitude | Unicast | Replying with ACK message | ```JOIN_REPLY;12DE;52.342;43.242```
|FETCH|source address|Unicast|Replying with FETCH_REPLY message| ```FETCH;12DE```
|FETCH_REPLY|source address, Number of all NS entries, checksums from all NS entries | Unicast| Replying with ACK message |```FETCH_REPLY;12DE;4;1234;2332;2300;1900```
| PULL | source address, checksum of NS entry | Unicast | Replying with PULL and the request NS entry | ```PULL;12DE;3432```
| PUSH | source address, source address of the specific hop, direct attached hop, latitude, longitude | Unicast | Replying with ACK message | ```PUSH;12DE;12EF;34ED;45DE;43.232;54.232```
| LEAVE  | source address | Unicast | Mark corresponding entry as "DOWN" to NS | ```LEAVE;12DE``` 
| MOVE  | source address, latitude, longitude | Broadcast | Replying with ACK message | ```MOVE;12DE;52.342;43.242```
| ACK  | source address | Unicast | perform operation on NS, either add an entry or update an existing entry | ```ACK;12DE```

