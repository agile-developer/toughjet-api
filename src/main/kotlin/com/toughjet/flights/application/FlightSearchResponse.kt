package com.toughjet.flights.application

import com.toughjet.flights.domain.Flight
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneOffset

data class FlightSearchResponse(
    val carrier: String,
    val basePrice: BigDecimal,
    val tax: BigDecimal,
    val discount: String,
    val departureAirportName: String,
    val arrivalAirportName: String,
    val outboundDateTime: Instant,
    val inboundDateTime: Instant
) {
    companion object {
        fun fromFlight(flight: Flight) =
            with(flight) {
                FlightSearchResponse(
                    carrier,
                    BigDecimal.valueOf(basePriceInPence).movePointLeft(2),
                    BigDecimal.valueOf(taxInPence).movePointLeft(2),
                    "$discountInPercent%",
                    departureAirportName,
                    arrivalAirportName,
                    outboundDateTime.atOffset(ZoneOffset.UTC).toInstant(),
                    inboundDateTime.atOffset(ZoneOffset.UTC).toInstant()
                )
            }
    }
}
