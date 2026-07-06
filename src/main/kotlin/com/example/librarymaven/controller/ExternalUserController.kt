package com.example.librarymaven.controller

import com.example.librarymaven.dto.ExternalUserDTO
import com.example.librarymaven.dto.todoDTO
import com.example.librarymaven.service.ExternalUserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/external")
class ExternalUserController(
    private val externalUserService: ExternalUserService
) {

    @GetMapping("/users")
    fun getUsers(): List<ExternalUserDTO> {
        return externalUserService.getUsers()
    }

    @GetMapping("todo")
    fun getTodo(): List<todoDTO> {
        return externalUserService.getTodo()
    }

    @PutMapping("/todo/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @RequestBody request: todoDTO
    ): todoDTO {
        return externalUserService.updateTodo(id, request)
    }

    @PostMapping("/todo")
    fun createTodo(@RequestBody request: todoDTO): todoDTO {
        return externalUserService.createTodo(request)
    }
}