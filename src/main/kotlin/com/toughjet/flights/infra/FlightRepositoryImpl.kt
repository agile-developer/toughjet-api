package com.toughjet.flights.infra

import com.toughjet.flights.domain.Flight
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository

@Repository
class FlightRepositoryImpl(
    private val jdbcClient: JdbcClient
) : FlightRepository {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun findFlights(origin: String, destination: String, passengers: Int): List<Flight> {
        logger.info("Finding flights between $origin and $destination for $passengers passengers")

        val query = """
            SELECT * FROM flights f
            WHERE f.departure_airport_name = ?
            AND f.arrival_airport_name = ?
            AND f.seats_available >= ?
        """.trimIndent()
        return jdbcClient.sql(query)
            .params(origin, destination, passengers)
            .query { rs, _ ->
                Flight(
                    rs.getInt("id"),
                    rs.getString("carrier"),
                    rs.getLong("base_price_in_pence"),
                    rs.getString("departure_airport_name"),
                    rs.getString("arrival_airport_name"),
                    rs.getTimestamp("outbound_date_time").toLocalDateTime(),
                    rs.getTimestamp("inbound_date_time").toLocalDateTime(),
                    rs.getInt("seats_available")
                )
            }.list()
    }
}
