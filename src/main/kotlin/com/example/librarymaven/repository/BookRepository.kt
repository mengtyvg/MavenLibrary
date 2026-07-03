package com.example.librarymaven.repository

import com.example.librarymaven.dto.BookResponseDTO
import com.example.librarymaven.entity.BookEntity
import com.example.librarymaven.entity.BookStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.awt.print.Book


@Repository
interface BookRepository : JpaRepository<BookEntity, Long> {

    fun existsByIsbn(isbn: String?): Boolean
    //fun existsByIdAndIsbn(isbn: String, id: Long): Boolean
    //fun findByStatus(status: BookStatus): List<BookEntity>
    fun existsByIsbnAndIdNot(isbn: String?, id: Long): Boolean
    fun findByBookStatus(bookStatus: String, pageable: Pageable): Page<BookEntity>
    fun findByStatusTrue(): List<BookEntity>

}
