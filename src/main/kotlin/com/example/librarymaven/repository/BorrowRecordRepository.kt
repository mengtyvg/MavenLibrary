package com.example.librarymaven.repository

import com.example.librarymaven.entity.BorrowRecordEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BorrowRecordRepository : JpaRepository<BorrowRecordEntity, Long> {
    fun findByMemberId(memberId: Long): List<BorrowRecordEntity>
    fun findByBookId(bookId: Long): List<BorrowRecordEntity>
}