# 1. APPLICATION

## 1.1 Open API and code generation 
The open API contract is : `src/main/resources/bookingQueryApi.yaml`

Code generator is configured in the pom.xml file. It happens using plugin : `openapi-generator-maven-plugin`

Code source is generated in api and model packages : 
`com.cola.booking.query.application.api`
`com.cola.booking.query.application.model`

## 1.2 Kafka Listener
Kafka consumer allow subscribing th kafka topic and listen to notification arriving

For each kafka record, based on type = create or cancel
When 
* type = create => add an entry in history for the slot and the user
* type = cancel => remove an entry from the history for the slot and the user


## 1.3 Controllers
* SlotController : gives the slots availability
* UserController : gives the history of bookings for a user


# 2. DOMAIN
## opening
## Booking histoty
## Slots

# 3. INFRASTRUCTURE

## 3.3. Openings 
At the starting of the application the service OpeningSrore loks to the configuration 

opening.startTime=HH:mm:ss      => first time available in the day 
opening.endTime=HH:mm:ss        => last time available in the day
opening.duration=minutes        => the duration of each opening
room.names                      => the room names that are displayed