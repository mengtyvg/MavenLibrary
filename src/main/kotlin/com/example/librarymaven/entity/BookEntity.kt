package com.example.librarymaven.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

enum class BookStatus {
    BORROW,
    RETURN,
    NEW
}

@Entity
@Table(name = "books")
data class BookEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String?,
    var author: String?,
    var isbn: String?,
    var bookStatus: String? = BookStatus.NEW.name,
    var status: Boolean? = true,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
