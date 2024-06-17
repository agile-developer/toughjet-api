package com.toughjet.flights.domain

import com.toughjet.flights.application.FlightSearchRequest

interface FlightSearchService {

    fun searchFlights(searchRequest: FlightSearchRequest): SearchResult
}

sealed interface SearchResult {
    data class Found(val flights: List<Flight>): SearchResult
    data object Empty: SearchResult {
        const val NO_RESULTS = "Search returned no results"
    }
    data class UnsupportedAirport(val message: String): SearchResult
}
