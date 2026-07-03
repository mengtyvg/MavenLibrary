package com.example.librarymaven.dto

import com.example.librarymaven.entity.BookStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


data class CreateBookRequestDTO(
    @field:NotBlank(message = "Title cannot be empty")
    val title: String? = null,

    @field:NotBlank(message = "Author cannot be empty")
    val author: String? = null,

    @field:NotBlank(message = "ISBN cannot be empty")
    val isbn: String? = null,

//    @field:NotNull(message = "Book status cannot be nothing")
    val bookStatus: BookStatus? = null
)

data class BookResponseDTO(
    val id: Long?,
    val title: String?,
    val author: String?,
    val isbn: String?,
    val bookStatus: BookStatus?,
    val updatedDate: String?
)