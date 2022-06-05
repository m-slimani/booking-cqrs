# 1. APPLICATION

## 1.1 Open API and code generation 
The open API contract is : `src/main/resources/bookingCommandApi.yaml`

Code generator is configured in the pom.xml file. It happens using plugin : `openapi-generator-maven-plugin`

Code source is generated in api and model packages : 
`com.cola.booking.command.application.api`
`com.cola.booking.command.application.model`

swagger-ui => http://localhost:8081/swagger-ui/index.html

## 1.2 Kafka Listener
Kafka producer allow publishing messages in kafka topic

For each REST operation, a type = create or cancel is added to the message based on the POST or DELETE verbs
* type = create => for each POST bookings events
* type = cancel => for DELETE bookings events


## 1.3 Controllers
* BookingController : to submit create and delete requests

# 2. DOMAIN
## Booking
## Events

# 3. INFRASTRUCTURE

## 3.1. Booking database table
## 3.2 Kafka producer