package com.toughjet.flights

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestContainersConfiguration {

	@Bean
	@ServiceConnection
	fun postgresContainer(): PostgreSQLContainer<*> {
		return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
	}

}
