package com.toughjet.flights.domain

import com.toughjet.flights.application.FlightSearchRequest
import com.toughjet.flights.infra.FlightRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FlightSearchServiceImpl(
    @Value("\${app.percentage.discount}")
    private val discount: Int,
    @Value("\${app.percentage.tax}")
    private val tax: Int,
    private val flightRepository: FlightRepository
) : FlightSearchService {

    private val logger = LoggerFactory.getLogger(javaClass)

    init {
        logger.info("Configured discount: $discount%")
        logger.info("Configured tax: $tax%")
//        val zoneIds = ZoneId.getAvailableZoneIds()
//        zoneIds.forEach { logger.info(it) }
//        logger.info("The system default is ${ZoneId.systemDefault()}")
    }

    override fun searchFlights(searchRequest: FlightSearchRequest): SearchResult {
        logger.info("Searching for flights")
        val result = runCatching {
            checkAirport(searchRequest.from, "Origin")
            checkAirport(searchRequest.to, "Destination")
            flightRepository.findFlights(
                searchRequest.from, searchRequest.to, searchRequest.numberOfAdults
            ).filter {
                it.outboundDateTime.toLocalDate().isEqual(searchRequest.outboundDate) &&
                        it.inboundDateTime.toLocalDate().isEqual(searchRequest.inboundDate)
            }.map { addTaxAndDiscount(it) }
        }.fold(
            onSuccess = {
                if (it.isEmpty()) {
                    logger.info("Search returned zero results")
                    SearchResult.Empty
                } else {
                    SearchResult.Found(it)
                }
            },
            onFailure = {
                logger.error("Error encountered searching for flights: ${it.message!!}")
                SearchResult.UnsupportedAirport(it.message!!)
            }
        )

        return result
    }

    private fun checkAirport(airportCode: String, journeyLeg: String): Airport {
        return runCatching {
            Airport.valueOf(airportCode)
        }.getOrElse {
            throw IllegalArgumentException("$journeyLeg: $airportCode is not a supported airport code")
        }
    }

    private fun addTaxAndDiscount(flight: Flight): Flight {
        return Flight(
            flight.id,
            flight.carrier,
            flight.basePriceInPence,
            flight.departureAirportName,
            flight.arrivalAirportName,
            flight.outboundDateTime,
            flight.inboundDateTime,
            flight.seatsAvailable,
            discount,
            tax
        )
    }
}
