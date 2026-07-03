package com.example.librarymaven.controller

import com.example.librarymaven.dto.BookResponseDTO
import com.example.librarymaven.dto.CreateBookRequestDTO
import com.example.librarymaven.entity.BookEntity
import com.example.librarymaven.entity.BookStatus
import com.example.librarymaven.service.BookService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService
) {

    @GetMapping("/all")
    fun getAllBooks(): List<BookResponseDTO> {
        return bookService.getAllBooks()
    }

    @GetMapping("/page")
    fun getAllBooks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int,
        @RequestParam(required = false) status: BookStatus?,
        @RequestParam(required = false) sort: String?
    ): Page<BookEntity> {
        return bookService.getAllBooksWithPage(page, size, status, sort)
    }




    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): BookResponseDTO? {
        return bookService.findByID(id)
    }

//    @GetMapping
//    fun getBookByStatus(@RequestParam status: BookStatus): List<BookEntity> {
//        return bookService.getBookByStatus(status)
//    }

    @PostMapping("/create")
    fun createBook(@Valid @RequestBody createBookRequestDTO: CreateBookRequestDTO): BookResponseDTO {
        return bookService.createBook(createBookRequestDTO)
    }

    @PutMapping("/update/{id}")
    fun updateBook(
        @PathVariable id: Long,
        @Valid @RequestBody request: CreateBookRequestDTO
    ): BookResponseDTO {
        return bookService.updateBook(id, request)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteBook(@PathVariable("id") id: Long): String {
        bookService.deleteBook(id)
        return "book successfully deleted"
    }



}