package com.example.librarymaven.dto


data class ExternalUserDTO(
    val id: Long?,
    val name: String?,
    val username: String?,
    val email: String?,
    val phone: String?,
    val website: String?,
    val address: ExternalAddressDTO
)

data class ExternalAddressDTO(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
)

data class todoDTO(
    val userId: Long?,
    val id: Long?,
    val title: String?,
    val completed: Boolean?
)

