package com.example.librarymaven.service


import com.example.librarymaven.dto.ExternalUserDTO
import com.example.librarymaven.dto.todoDTO

interface ExternalUserService {
    fun getUsers(): List<ExternalUserDTO>

    fun getTodo(): List<todoDTO>

    fun updateTodo(id: Long, request: todoDTO): todoDTO

    fun createTodo(request: todoDTO): todoDTO
}