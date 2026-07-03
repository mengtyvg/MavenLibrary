package com.example.librarymaven.controller

import com.example.librarymaven.dto.BorrowRecordResponseDTO
import com.example.librarymaven.dto.CreateBorrowRecordRequestDTO
import com.example.librarymaven.service.BorrowRecordService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/borrow-records")
class BorrowRecordController(
    private val borrowRecordService: BorrowRecordService
) {

    @PostMapping("/borrow")
    fun borrowBook(
        @RequestBody request: CreateBorrowRecordRequestDTO
    ): BorrowRecordResponseDTO {
        return borrowRecordService.borrowBook(request)
    }

    @PutMapping("/return/{borrowRecordId}")
    fun returnBook(
        @PathVariable borrowRecordId: Long
    ): BorrowRecordResponseDTO {
        return borrowRecordService.returnBook(borrowRecordId)
    }

    @GetMapping("/all")
    fun getAllBorrowRecords(): List<BorrowRecordResponseDTO> {
        return borrowRecordService.getAllBorrowRecords()
    }

    @GetMapping("/member/{memberId}")
    fun getBorrowRecordsByMemberId(
        @PathVariable memberId: Long
    ): List<BorrowRecordResponseDTO> {
        return borrowRecordService.getBorrowRecordsByMemberId(memberId)
    }
}