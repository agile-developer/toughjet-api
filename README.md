# ToughJet API

### Summary
This is simple service that provides a single endpoint to search for flights. The service uses a Postgres database to store and retrieve flight data. There are currently no endpoints to add flight data, so flight data is populated using Liquibase migration scripts.

### Running the application
This project is implemented in Kotlin (`1.9.24`) and compiled for Java 21. Please ensure the target machine has Java 21 installed, and `java` is available on the `PATH`. It also uses Postgres for data, so a Postgres server must be available on or accessible from the local machine. The simplest way is to run Postgres as a Docker container. You'll need Docker Desktop, Podman, Rancher or some other OCI compliant runtime that can run container images.

For Docker, I've used the following command:
```shell
docker run -d \
--name toughjet-api \
-p 5434:5432 \
-e POSTGRES_DB=toughjet-db \
-e POSTGRES_USER=toughjet-user \
-e POSTGRES_PASSWORD=this1isNotGood! \
postgres:latest
```
**Important**: The database must be available before trying to run the application. The Postgres port `5432` is mapped to local port `5434` because we want to run multiple Postgres servers.

Once you've cloned the repository, or unzipped the source directory, change to the `toughjet-api` folder and run the following command, to build sources:
```shell
./gradlew clean build
```
To start the application, run:
```shell
java -jar build/libs/toughjet-api-0.0.1-SNAPSHOT.jar
```
This should start the application listening on port `8082` of your local machine. To test the endpoint use the following `curl` command:
```shell
curl -v \
-H 'Content-Type: application/json' \
-d '{"from":"LHR", "to":"DXB", "outboundDate":"2024-07-01", "inboundDate":"2024-07-10", "numberOfAdults":3}' \
http://localhost:8082/toughjet/flights
```
This should return a response like this:
```json
[
  {
    "carrier": "Emirates",
    "basePrice": 850.00,
    "tax": 127.50,
    "discount": "5%",
    "departureAirportName": "LHR",
    "arrivalAirportName": "DXB",
    "outboundDateTime": "2024-07-01T08:00:00Z",
    "inboundDateTime": "2024-07-10T08:00:00Z"
  },
  {
    "carrier": "British Airways",
    "basePrice": 750.00,
    "tax": 112.50,
    "discount": "5%",
    "departureAirportName": "LHR",
    "arrivalAirportName": "DXB",
    "outboundDateTime": "2024-07-01T08:00:00Z",
    "inboundDateTime": "2024-07-10T08:00:00Z"
  },
  {
    "carrier": "Turkish Airlines",
    "basePrice": 650.00,
    "tax": 97.50,
    "discount": "5%",
    "departureAirportName": "LHR",
    "arrivalAirportName": "DXB",
    "outboundDateTime": "2024-07-01T08:00:00Z",
    "inboundDateTime": "2024-07-10T08:00:00Z"
  },
  {
    "carrier": "Lufthansa",
    "basePrice": 450.00,
    "tax": 67.50,
    "discount": "5%",
    "departureAirportName": "LHR",
    "arrivalAirportName": "DXB",
    "outboundDateTime": "2024-07-01T08:00:00Z",
    "inboundDateTime": "2024-07-10T08:00:00Z"
  }
]
```
