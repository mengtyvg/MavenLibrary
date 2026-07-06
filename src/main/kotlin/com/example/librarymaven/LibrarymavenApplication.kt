package com.example.librarymaven

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching



@EnableCaching
@SpringBootApplication
class LibrarymavenApplication

fun main(args: Array<String>) {
	runApplication<LibrarymavenApplication>(*args)
}
