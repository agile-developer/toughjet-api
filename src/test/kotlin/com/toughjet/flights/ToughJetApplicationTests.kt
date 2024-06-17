package com.toughjet.flights

import com.toughjet.flights.application.FlightSearchRequest
import com.toughjet.flights.domain.FlightSearchService
import com.toughjet.flights.domain.SearchResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.simple.JdbcClient
import java.time.LocalDate

@Import(TestContainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ToughJetApplicationTests {

    @Autowired
    private lateinit var flightSearchService: FlightSearchService

    @Autowired
    private lateinit var jdbcClient: JdbcClient

//	@Test
//	fun contextLoads() {
//	}

    @BeforeEach
    fun init() {
        jdbcClient.sql("DELETE FROM flights").update()
    }

    @Test
    fun `should retrieve only records that satisfy search criteria`() {
        // arrange
        val query1 = """
			INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
			VALUES ('Emirates', 'LHR', 'DXB', '2024-09-01 08:00:00', '2024-09-10 08:00:00', 85000, 11);
		""".trimIndent()
        val query2 = """
			INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
			VALUES ('British Airways', 'LHR', 'DXB', '2024-09-01 08:00:00', '2024-09-10 08:00:00', 85000, 11);
		""".trimIndent()
        val query3 = """
			INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
			VALUES ('Turkish Airlines', 'LHR', 'DXB', '2024-09-01 08:00:00', '2024-09-10 08:00:00', 85000, 11);
		""".trimIndent()
        val query4 = """
			INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
			VALUES ('Lufthansa', 'LHR', 'DXB', '2024-07-01 08:00:00', '2024-07-10 08:00:00', 85000, 11);
		""".trimIndent()
        val query5 = """
			INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
			VALUES ('KLM', 'LHR', 'DXB', '2024-07-01 08:00:00', '2024-07-10 08:00:00', 85000, 11);
		""".trimIndent()
        jdbcClient.sql(query1).update()
        jdbcClient.sql(query2).update()
        jdbcClient.sql(query3).update()
        jdbcClient.sql(query4).update()
        jdbcClient.sql(query5).update()
        val from = "LHR"
        val to = "DXB"
        val outboundDate = LocalDate.of(2024, 9, 1)
        val inboundDate = LocalDate.of(2024, 9, 10)
        val numberOfAdults = 4
        val searchRequest = FlightSearchRequest(from, to, outboundDate, inboundDate, numberOfAdults)

        // act
        val response = flightSearchService.searchFlights(searchRequest)

        // assert
        assertThat(response).isInstanceOf(SearchResult.Found::class.java)
        val searchResult = response as SearchResult.Found
        assertThat(searchResult.flights.size).isEqualTo(3)
    }

    @Test
    fun `should calculate tax value from base price using configured tax percentage`() {
        // arrange
        val query1 = """
			INSERT INTO flights (carrier, departure_airport_name, arrival_airport_name, outbound_date_time, inbound_date_time, base_price_in_pence, seats_available)
			VALUES ('Emirates', 'LHR', 'DXB', '2024-09-01 08:00:00', '2024-09-10 08:00:00', 85000, 11);
		""".trimIndent()
        jdbcClient.sql(query1).update()
        val from = "LHR"
        val to = "DXB"
        val outboundDate = LocalDate.of(2024, 9, 1)
        val inboundDate = LocalDate.of(2024, 9, 10)
        val numberOfAdults = 4
        val searchRequest = FlightSearchRequest(from, to, outboundDate, inboundDate, numberOfAdults)

        // act
        val response = flightSearchService.searchFlights(searchRequest)

        // assert
        val searchResult = response as SearchResult.Found
        val flight = searchResult.flights[0]
        val taxInPercent = flight.taxInPercent
        val basePriceInPence = flight.basePriceInPence
        val expectedTaxInPence = basePriceInPence * (taxInPercent / 100.00)
        assertThat(flight.taxInPence).isEqualTo(expectedTaxInPence.toLong())
    }
}
