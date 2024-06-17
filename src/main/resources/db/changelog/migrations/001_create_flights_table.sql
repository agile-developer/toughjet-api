create table if not exists flights
(
    id serial primary key not null,
    carrier varchar(255) not null,
    departure_airport_name char(3) not null,
    arrival_airport_name char(3) not null,
    outbound_date_time timestamp not null,
    inbound_date_time timestamp not null,
    base_price_in_pence integer not null,
    seats_available integer not null
);

INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
VALUES ('Emirates', 'LHR', 'DXB', '2024-07-01 08:00:00', '2024-07-10 08:00:00', 85000, 11);
INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
VALUES ('British Airways', 'LHR', 'DXB', '2024-07-01 08:00:00', '2024-07-10 08:00:00', 75000, 4);
INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
VALUES ('Turkish Airlines', 'LHR', 'DXB', '2024-07-01 08:00:00', '2024-07-10 08:00:00', 65000, 21);
INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
VALUES ('Lufthansa', 'LHR', 'DXB', '2024-07-01 08:00:00', '2024-07-10 08:00:00', 45000, 3);
INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
VALUES ('KLM', 'LHR', 'DXB', '2024-07-01 08:00:00', '2024-07-10 08:00:00', 35000, 0);
