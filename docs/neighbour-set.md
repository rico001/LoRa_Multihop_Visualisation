# Neighbour Set (NS)
The neighbour set is the internal information table of a single hop.
Every node participating in the network is listed and updated in the neighbour set table.

## Neighbour Set definition
The following information are stored in the neighbour set:

* UID 
* Address
* DAH (directly attached hop)
* Latitude
* Longitude
* State 
* Timestamp

### UID
* **unique identifier**
* represents an entry in the neighbour set
* is a 4 digit integer value automatically genereated by the Application
* range: ```[ 0 - 9999 ] ```
* its use is only for internal purposes
* used for referring to entrys in the NS table (inserting, updating)
* ```0000`` is reserved for the local hop


### Address
* represents the address of single hop
* is a 4 digit hexadecimal value
* range: ```[ 0 - FFE ] ```

### DAH
* **directly attached hop**
* is a 4 digit hexadecimal value
* range: ```[ 0 - FFE ] ```

### Latitude/ Longitude 
* represents the position of a single hop
* has 3 digits and 3 digits after decimal point ( = float, double)
* range: ```[ 0 - 179,999 ] ```

### State
* represents the current state of the hop in the network
* possible states are:

#### UP
* the current entry is up to date
* the nodes parameter match with the NS entry
* there are no pending messages to evaluate

#### DOWN
* the corresponding hop has been removed from the network

#### PENDING
* the current enty is pending, because there a messages to proceed in the NDP message queue
* after the specific messages has been evaluated, 

#### UNKNOWN
* due to circumstances the state of the entry and thus the hop is unknown
* ...
 

### TIMESTAMP
* UNIX timestamp to represent the actuality of an entry
* the defined timestamp format is ```YYYY-DD-MM HH:mm:ss```
 
### Example Neighbour set

| UID  | Address | DAH | LAT | LONG | STATE | TIMESTAMP | |
|---| --- | --- | --- | --- | --- | --- | --- |
| 0000 | 56ED | 32AV | 54,323 | 34,243 | UP | 2019-05-19 09:24:45.4 | 
| 0001  | 34DE | 56DE | 54,233 | 44,342 | UP |  2019-05-19 09:24:45.4 |
| 0002  | 23FF | 34DE | 54,233 | 44,342 | DOWN |  2019-05-19 09:25:34.0 |
| 0003  | 01EF | 23FF | 54,233 | 44,342 | UP |  2019-05-19 08:12:34.0 | 
