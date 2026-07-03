package com.example.librarymaven.dto


import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateMemberRequestDTO(
    @field:NotBlank(message = "Name cannot be empty")
    val name: String? = null,

    @field:NotBlank(message = "Email cannot be empty")
    @field:Email(message = "Email format is invalid")
    val email: String? = null,

    @field:NotBlank(message = "Phone cannot be empty")
    val phone: String? = null
)

data class MemberResponseDTO(
    val id: Long?,
    val name: String?,
    val email: String?,
    val phone: String?,
    val status: Boolean?
)