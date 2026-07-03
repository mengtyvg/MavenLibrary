package com.example.librarymaven.service


import com.example.librarymaven.dto.BorrowRecordResponseDTO
import com.example.librarymaven.dto.CreateBorrowRecordRequestDTO

interface BorrowRecordService {

    fun borrowBook(request: CreateBorrowRecordRequestDTO): BorrowRecordResponseDTO

    fun returnBook(borrowRecordId: Long): BorrowRecordResponseDTO

    fun getAllBorrowRecords(): List<BorrowRecordResponseDTO>

    fun getBorrowRecordsByMemberId(memberId: Long): List<BorrowRecordResponseDTO>
}