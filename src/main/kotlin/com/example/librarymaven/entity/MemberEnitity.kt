package com.example.librarymaven.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "members")
data class MemberEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,

    @Column(unique = true)
    var email: String? = null,

    var phone: String? = null,

    var status: Boolean? = true,

    var createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now()
)