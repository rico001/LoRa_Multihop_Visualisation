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
| JOIN  | source address, latitude, longitude| Broadcast | Add new entry to NS| 
| JOIN_REPLY | source address, latitude, longitude | Unicast | Add new entry to NS
| LEAVE  | source address | Unicast | Mark corresponding entry as "DOWN" to NS | 
| MOVE  | source address, latitude, longitude | Unicast | Update entrys position values n NS | 
| ACK  | source address | Unicast | confirmation needed to apply changes in NS| 

## Messaga Flow
### JOIN
```
    |---------->|   [A] => JOIN(B)(address(A),latitude(a),longitude(a))
    |           |
    |<----------|   [B] => JOIN_REPLY(A)(address(B), latitiude(b), longitude(b))
    |           |
    |---------->|   [A] => ACK(B)(address(A))
Node A      Node B
```

This results for Node A in:
```
addNewHop(address(b), latitude(b), longitude(b), timestamp)
```

and for Node B in:
```
addNewHop(address(a), latitude(a), longitude(a), timestamp)
```