package com.example.librarymaven.service.impl

import com.example.librarymaven.dto.ExternalUserDTO
import com.example.librarymaven.dto.todoDTO
import com.example.librarymaven.service.ExternalUserService
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


@Service
class ExternalUserServiceImpl(
    private val restTemplate: RestTemplate
) : ExternalUserService {

    override fun getUsers(): List<ExternalUserDTO> {
        val url = "https://jsonplaceholder.typicode.com/users"

        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<ExternalUserDTO>>() {}
        )

        return response.body ?: emptyList()
    }

    override fun getTodo(): List<todoDTO>{
        val url = "https://jsonplaceholder.typicode.com/todos"

        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<todoDTO>>(){}
        )
        return response.body ?: emptyList()
    }

    override fun updateTodo(id: Long, request: todoDTO): todoDTO {
        val url = "https://jsonplaceholder.typicode.com/todos/$id"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val body = todoDTO(
            userId = request.userId,
            id = id,
            title = request.title,
            completed = request.completed
        )

        val entity = HttpEntity(body, headers)

        val response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            entity,
            todoDTO::class.java
        )

        return response.body ?: throw RuntimeException("External API returned empty response")

}

    override fun createTodo(request: todoDTO): todoDTO {
        val url = "https://jsonplaceholder.typicode.com/todos"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val body = todoDTO(
            userId = request.userId,
            title = request.title,
            id = null,
            completed = request.completed
        )

        val entity = HttpEntity(body, headers)
        val response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            todoDTO::class.java
        )

        return response.body ?: throw RuntimeException("External API returned empty response")

    }

}