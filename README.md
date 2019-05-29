# instrument-pricing
Tracks price of instrument

## Solution
In this solution, I have assumed that the producer will put a message in a message-broker (say SQS). The message would 
 contain the number of records that are supposed to be produced so that the consumer knows when to stop.
 
 To prevent a consumer from waiting infinitely, there is a timeout for each thread. Value of timeout is configurable in `application.properties` file.
 
 We can invoke `/produce` end point which will generate a random number of files in a temporary directory. Once production is
 done, producer will put a message containing the folder path and the number of files to be generated.
 
 The consumer would keep pinging the messagebroker every 50ms and ask for a new message. Once a message is received, the 
 consumer will list all files in the directory and upload the instrument data in an In-Memory data store.
 
 ## Design
 
 Please check out the `intractions.puml`  or (a plant uml plugin would be required) `interactions.png` diagram for easy understanding of how different components are interacting. 
 
 ![interactions](./interactions.png)
 ## Things not done
 - Update of the price of an instrument.
 - clean up of temporary directory created by producer
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