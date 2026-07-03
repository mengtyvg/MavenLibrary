package com.example.librarymaven.service.impl

import com.example.librarymaven.dto.BorrowRecordResponseDTO
import com.example.librarymaven.dto.CreateBorrowRecordRequestDTO
import com.example.librarymaven.entity.BookStatus
import com.example.librarymaven.entity.BorrowRecordEntity
import com.example.librarymaven.repository.BookRepository
import com.example.librarymaven.repository.BorrowRecordRepository
import com.example.librarymaven.repository.MemberRepository
import com.example.librarymaven.service.BorrowRecordService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class BorrowRecordServiceImpl(
    private val borrowRecordRepository: BorrowRecordRepository,
    private val bookRepository: BookRepository,
    private val memberRepository: MemberRepository
) : BorrowRecordService {

    override fun borrowBook(request: CreateBorrowRecordRequestDTO): BorrowRecordResponseDTO{
        val bookId = request.bookId
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Book ID is required")

        val memberId = request.memberId
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Member ID is required")

        val book = bookRepository.findById(bookId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book ID $bookId not found"
                )
            }

        val member = memberRepository.findById(memberId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Member ID $memberId not found"
                )
            }

        val borrowRecord = BorrowRecordEntity(
            book = book,
            member = member,
            status = "BORROWED"
        )

        val savedBorrowRecord = borrowRecordRepository.save(borrowRecord)

        return toBorrowRecordResponseDTO(savedBorrowRecord)




    }

    override fun returnBook(id: Long): BorrowRecordResponseDTO{
        TODO("Not yet implemented")

    }

    override fun getAllBorrowRecords(): List<BorrowRecordResponseDTO> {
        return borrowRecordRepository.findAll()
            .map { toBorrowRecordResponseDTO(it) }
    }

    override fun getBorrowRecordsByMemberId(memberId: Long): List<BorrowRecordResponseDTO> {
        return borrowRecordRepository.findByMemberId(memberId).map { toBorrowRecordResponseDTO(it) }
    }





    private fun toBorrowRecordResponseDTO(record: BorrowRecordEntity): BorrowRecordResponseDTO {
        return BorrowRecordResponseDTO(
            id = record.id,
            bookId = record.book?.id,
            bookTitle = record.book?.title,
            memberId = record.member?.id,
            memberName = record.member?.name,
            borrowDate = record.borrowDate,
            returnDate = record.returnDate,
            status = record.status
        )
    }






}