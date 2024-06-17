package com.toughjet.flights

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestContainersConfiguration::class)
@SpringBootTest
class ToughJetApplicationTests {

	@Test
	fun contextLoads() {
	}

}
