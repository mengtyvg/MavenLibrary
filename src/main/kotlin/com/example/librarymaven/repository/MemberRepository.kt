package com.example.librarymaven.repository

import com.example.librarymaven.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun existsByEmail(email: String?): Boolean
    fun existsByEmailAndIdNot(email: String?, id: Long): Boolean
}