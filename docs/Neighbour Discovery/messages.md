# Protocol Messages
This document describes protocol messages used in the neighbour discovery process.

## Message definition
A message consists of the following:

* name
* set of params
* type(Broadcast, Multicast, Unicast)
* resulting actions, when proceeding such a message


## Declared Messages

| Name  | Params | Type | Resulting Actions ||
|---| --- | --- | --- |  --- | 
| JOIN  | address, latitude, longitude| Broadcast | Add new entry in neighbour set| 
| LEAVE  | address | Unicast | Mark corresponding entry as "DOWN" in neighbour set | 
| MOVE  | address, latitude, longitude | Unicast | Update entrys position values in neighbour set | 
| JOIN  | | | | 
| JOIN  | | | | 
| JOIN  | | | | 

