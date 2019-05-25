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
| MOVE  | source address, latitude, longitude | Broadcast | Update entrys position values n NS | 
| ACK  | source address | Unicast | confirmation needed to apply changes in NS| 

## Message Flow
### JOIN
* a JOIN Message is triggered, when a node enters the ad-hoc network
* The **first** node, who replies to the specific JOIN message, receives an ACK message
* The Node who has received an ACK message, in return of a JOIN_REPLY message is the corresponding destination of the node
* Both, source and destination node, add a new entry to the NS

```
    |---------->|   [A->B] => JOIN(address(A),latitude(a),longitude(a))
    |           |
    |<----------|   [B->A] => JOIN_REPLY(address(B), latitiude(b), longitude(b))
    |           |
    |---------->|   [A->B] => ACK(address(A))
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

### MOVE
* a MOVE message is triggered, when the position of the device has changed more than > 10 m.
* Nodes, who receive such a MOVE message, update the corresponding entry in the NS
* The MOVE message is going to be broadcasted again by receiving nodes

```
    |           |           |   
    |           |           |
    |---------->|           |   [A->*] => MOVE(address(A),latitude(a),longitude(a))
    |           |           |
    |           |---------->|   [B->*] => MOVE(address(A), latitude(a),longitude(a))   
Node A      Node B      Node X
```

This results for Node * in:
```
updateEntry(uid)
```

### LEAVE
* a LEAVE message is triggered, when the corresponding endpoint has requested such action
* Nodes, who receive such a LEAVE message, update the corresponding entry in the NS (state = DOWN)
* The LEAVE message is going to be broadcasted again by receiving nodes

```
    |           |           |   
    |           |           |
    |---------->|           |   [A->*] => LEAVE(address(A))
    |           |           |
    |           |---------->|   [B->*] => LEAVE(address(A))   
Node A      Node B      Node X
```

This results for Node * in:
```
updateEntry(uid)
```
