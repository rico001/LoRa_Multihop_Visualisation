# Protocol States
This document describes states the neighbour discovery algorithm consists of.
The NDP has 4 states / phases:

### I. Joining
A node joins the ad hoc network, locates a destination hop, and obtains all his neighbour sets entries.

### II. Moving 
A node has changed its position, then a MOVE message is triggered, to inform other nodes.

### III. Verifying
Periodically, a node reuqests via a FETCH messages to its destination hop, for network updates. 

### IV. Leaving 
A Node leaves the network.

## I. Joining
The Joining state consists of:

* JOIN
* FETCH
* PULL 

communications.

### JOIN
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

### FETCH
* a FETCH message is used to get meta information about the network from a hop
* instead of transmitting all information in the NS, checksums are used, this reduces the size of the message
* Nodes, who receive a FETCH_REPLY message, compare the checksum with the checksum of their NS, if they do dont match, it means the destination hop has a newer NS entry

```
    |---------->|   [A->B] => FETCH(address(A))
    |           |
    |<----------|   [B->A] => FETCH_REPLY(address(B), Number of NS entries, checksums)
    |           |
    |---------->|   [A->B] => ACK(address(A))
Node A      Node B
```

### PULL
* If a node has received checksums from FETCH_REPLY, which do not match, he can request to send the full NS entry 

```
    |---------->|   [A->B] => PULL(address(A),checksum)
    |           |
    |<----------|   [B->A] => PUSH(address(B), source address, destination address, latitiude(b), longitude(b))
    |           |
    |---------->|   [A->B] => ACK(address(A))
Node A      Node B
```

This results for Node A in either:

```
addNewHop(address(b), latitude(b), longitude(b), timestamp)
```

```
updateHop(address(b), latitude(b), longitude(b), timestamp)
```

## II. Moving
The Moving state consists of:

* MOVE
* PULL 

communications.

## III. Verifying
The Verifying state consists of:

* FETCH
* PULL 

communications.

## IV. Leavig
The Leaving state consists of:

* LEAVE
* PULL 

communications.

### II. MOVE
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
