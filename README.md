# instrument-pricing
Tracks price of instrument

## Solution
In this solution, I have assumed that the producer will put a message in a message-broker (say SQS). The message would 
 contain the number of records that are supposed to be produced so that the consumer knows when to stop.
 
 
 ## THings not done
 - Error handling on end points to return meaningful messages.
 - Logging is not configured