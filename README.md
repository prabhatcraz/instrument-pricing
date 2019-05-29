# instrument-pricing
Tracks price of instrument

## Solution
In this solution, I have assumed that the producer will put a message in a message-broker (say SQS). The message would 
 contain the number of records that are supposed to be produced so that the consumer knows when to stop.
 
 
 ## THings not done
 - Error handling on end points to return meaningful messages.
 - Logging is not configured
 
 
 ## Live demo steps
 ```bash
 # Build the jar and ru
mvn clean install && java -jar target/instrument-0.0.1-SNAPSHOT.jar

# Randomly produce some data.
curl -X POST http://localhost:8080/instruments

# See all instrument ids present
curl  http://localhost:8080/instruments

# See price of an instrument
curl  http://localhost:8080/instrument/<<Instrumentid>>
```