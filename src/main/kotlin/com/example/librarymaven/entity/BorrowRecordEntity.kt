package com.example.librarymaven.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "borrow_records")
data class BorrowRecordEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "book_id")
    var book: BookEntity? = null,

    @ManyToOne
    @JoinColumn(name = "member_id")
    var member: MemberEntity? = null,

    var borrowDate: LocalDateTime = LocalDateTime.now(),

    var returnDate: LocalDateTime? = null,

    var status: String? = "BORROWED"
)