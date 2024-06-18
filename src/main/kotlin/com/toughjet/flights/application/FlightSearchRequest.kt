package com.toughjet.flights.application

import java.time.LocalDate

data class FlightSearchRequest(
    val from: String,
    val to: String,
    val outboundDate: LocalDate,
    val inboundDate: LocalDate,
    val numberOfAdults: Int,
) {
    fun validate() {
        val validationErrors = mutableListOf<String>()
        if (from.isBlank() || from.length != 3) {
            validationErrors.add("Origin is invalid")
        }
        if (to.isBlank() || to.length != 3) {
            validationErrors.add("Destination is invalid")
        }
        if (outboundDate.isBefore(LocalDate.now())) {
            validationErrors.add("Departure date cannot be in the past")
        }
        if (inboundDate.isBefore(LocalDate.now())) {
            validationErrors.add("Return date cannot be in the past")
        }
        if (inboundDate.isBefore(outboundDate)) {
            validationErrors.add("Return date must be later than departure date")
        }
        if (numberOfAdults < 1) {
            validationErrors.add("Passenger count must be greater than zero")
        }

        if (validationErrors.isNotEmpty()) throw InvalidRequestException(validationErrors)
    }
}

