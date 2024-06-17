package com.toughjet.flights.infra

import com.toughjet.flights.domain.Flight

interface FlightRepository {
    fun findFlights(origin: String, destination: String, passengers: Int): List<Flight>
}