package com.example.librarymaven.service.impl

import com.example.librarymaven.dto.BookResponseDTO
import com.example.librarymaven.dto.CreateBookRequestDTO
import com.example.librarymaven.entity.BookEntity
import com.example.librarymaven.entity.BookStatus
import com.example.librarymaven.repository.BookRepository
import com.example.librarymaven.service.BookService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.collections.map


@Service
class BookServiceImpl(
    val bookRepository: BookRepository
) : BookService {

    private val formatter = DateTimeFormatter.ofPattern(
        "EEEE d MMMM yyyy, h:mm a",
        Locale.ENGLISH
    )


        // auto convert entity to dto
    private fun toBookResponseDTO(book: BookEntity): BookResponseDTO {
        return BookResponseDTO(
            id = book.id,
            title = book.title,
            author = book.author,
            isbn = book.isbn,
            bookStatus = BookStatus.valueOf(book.bookStatus ?: BookStatus.NEW.name),
            updatedDate = formatter.format(book.updatedAt)
        )
    }







    override fun getAllBooks(): List<BookResponseDTO> {
        return bookRepository.findAll()
            .map { toBookResponseDTO(it) }
            }


    override fun getAllBooksWithPage(page: Int, size: Int, status: BookStatus?, sort: String?): Page<BookResponseDTO> {

        val direction = if (sort?.lowercase() == "asc") {
            Sort.Direction.ASC
        } else {
            Sort.Direction.DESC
        }

        val pagable = PageRequest.of(
            page,
            size,
            Sort.by(direction, "createdAt")
        )

        return if (status != null) {
            bookRepository.findByBookStatus(status.name, pagable)
        } else {
            bookRepository.findAll(pagable)
        }
        .map { toBookResponseDTO(it) }
    }

    override fun findByID(id: Long): BookResponseDTO {
        val book = bookRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this ${id} not found")
            }

        return toBookResponseDTO(book)
    }

    override fun createBook(request: CreateBookRequestDTO): BookResponseDTO {
        val title = request.title?.trim()
        val author = request.author?.trim()
        val isbn = request.isbn?.trim()

        if (bookRepository.existsByIsbn(isbn)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Book with this ISBN already exists"
            )
        }

        val book = BookEntity(
            title = title,
            author = author,
            isbn = isbn
        )

        val savedBook = bookRepository.save(book)

        return toBookResponseDTO(savedBook)
    }

    override fun updateBook(id: Long, request: CreateBookRequestDTO): BookResponseDTO {
        val book = bookRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")
            }

        val title = request.title?.trim()
        val author = request.author?.trim()
        val isbn = request.isbn?.trim()
        val bookStatus = request.bookStatus
            ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Book status is required"
            )


        if (isbn.isNullOrBlank()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN Need a value")
        }



        if (bookRepository.existsByIsbnAndIdNot(isbn, id)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Book with this ISBN already exists"
            )
        }



        book.title = title
        book.author = author
        book.isbn = isbn
        book.bookStatus = bookStatus.name

        book.updatedAt = LocalDateTime.now()

        val updatedBook = bookRepository.save(book)

        //val formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy, HH:mm a", Locale.ENGLISH)


        return toBookResponseDTO(updatedBook)
    }

    override fun deleteBook(id: Long) {
        if (!bookRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")
        }

        return bookRepository.deleteById(id)
    }

    override fun softDeleteBook(id: Long): BookResponseDTO? {

        val book = bookRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")
            }

        book.status = false
        book.updatedAt = LocalDateTime.now()

        val deletedBook = bookRepository.save(book)

        return toBookResponseDTO(deletedBook)
    }
}
