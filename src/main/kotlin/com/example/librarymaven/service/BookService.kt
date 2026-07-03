package com.example.librarymaven.service

import com.example.librarymaven.dto.BookResponseDTO
import com.example.librarymaven.dto.CreateBookRequestDTO
import com.example.librarymaven.entity.BookEntity
import com.example.librarymaven.entity.BookStatus
import org.springframework.data.domain.Page

interface BookService {

    fun getAllBooks(): List<BookResponseDTO>

    fun getAllBooksWithPage(page: Int, size: Int, status: BookStatus?, sort: String?): Page<BookEntity>

    fun findByID(id: Long): BookResponseDTO?

    fun createBook(request: CreateBookRequestDTO): BookResponseDTO

    fun updateBook(id: Long, request: CreateBookRequestDTO): BookResponseDTO

    fun deleteBook(id: Long)
}