package com.example.librarymaven.dto

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateBorrowRecordRequestDTO(
    @field:NotNull(message = "Book id is required")
    val bookId: Long? = null,

    @field:NotNull(message = "Member id is required")
    val memberId: Long? = null
)

data class BorrowRecordResponseDTO(
    val id: Long?,
    val bookId: Long?,
    val bookTitle: String?,
    val memberId: Long?,
    val memberName: String?,
    val borrowDate: LocalDateTime?,
    val returnDate: LocalDateTime?,
    val status: String?
)