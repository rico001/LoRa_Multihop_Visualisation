# Neighbour Discovery Protocol message queue
Each node leads in internal NDP message queue.
Every message a node receives is going to be stored in the queue, until it has been proceeded.

Access principle is ```FIFO```. 

## Queue Operations
### push
* a message is appended onto the queue
### pop
* the message is removed from the queue
### isEmpty
* returns ```True``` if the queue is empty 