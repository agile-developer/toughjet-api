package com.toughjet.flights.domain

import java.time.LocalDateTime

data class Flight(
    val id: Int,
    val carrier: String,
    val basePriceInPence: Long,
    val departureAirportName: Airport,
    val arrivalAirportName: Airport,
    val outboundDateTime: LocalDateTime,
    val inboundDateTime: LocalDateTime,
    val seatsAvailable: Int,
    val discountInPercent: Int = 0,
    val taxInPercent: Int = 0
) {
    val taxInPence get(): Long {

        val calculatedTax = basePriceInPence * (taxInPercent / 100.00)
//        println("Base price: $basePriceInPence, Tax%: $taxInPercent% Calculated Tax: $calculatedTax")
        return calculatedTax.toLong()
    }
}
