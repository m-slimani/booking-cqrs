#Booking-cqrs-api

This application allow the following features:
1. Ability to see available rooms and available slots
2. Users can book meeting rooms by the hour (first come first served)
3. Users can cancel their own reservations


---------------
## Command
Rest Api that handles 
* create booking 
* cancel booking

Contract first approach by generating the Api code from the `bookingCommandApi.yaml`

---------------
## Query
Rest Api that handles 
* GET available slot
* GET user's bookings

Asynchronous communication by using a Kafka broker. This communication occurs on two sides :

- Command module send a `create` or `cancel` event inside a kafka topic.
- The message is consumed by the Query module and the query history is updated 

Contract first approach by generating the Api code from the `queryCommandApi.yaml`

### Slot initialisation
The following parameters are used to initialize the rooms and the opening slots

* `opening.startTime`=08:00:00  => beginning of the availability of the room
* `opening.endTime`=12:00:00 => end of the availability of the room
* `opening.duration`=60 => slot duration in minutes
* `room.names`=C01,P01  => the liste of room names, separated by comma 

---------------
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
