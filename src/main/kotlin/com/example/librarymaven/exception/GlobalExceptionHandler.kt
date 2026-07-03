package com.example.librarymaven.exception

import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // This function runs when @Valid fails
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationError(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, Any?>> {

        // Get first validation error
        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        // Create custom response
        val response = mapOf(
            "status" to 400,
            "error" to "Validation Failed",
            "message" to errors
        )

        // Return 400 Bad Request
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }


    //Show error on response validataion
    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(
        ex: ResponseStatusException
    ): ResponseEntity<Map<String, Any?>> {

        val response = mapOf(
            "status" to ex.statusCode.value(),
            "error" to "Request Failed",
            "message" to ex.reason
        )

        return ResponseEntity.status(ex.statusCode).body(response)
    }
}