package com.toughjet.flights

import org.springframework.boot.fromApplication


fun main(args: Array<String>) {
	fromApplication<ToughJetApplication>().with(TestContainersConfiguration::class.java).run(*args)
}
