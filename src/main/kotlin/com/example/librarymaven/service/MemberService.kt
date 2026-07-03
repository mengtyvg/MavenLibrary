package com.example.librarymaven.service

import com.example.librarymaven.dto.CreateMemberRequestDTO
import com.example.librarymaven.dto.MemberResponseDTO

interface MemberService {
    fun getAllMembers(): List<MemberResponseDTO>
    fun getMemberById(id: Long): MemberResponseDTO
    fun createMember(request: CreateMemberRequestDTO): MemberResponseDTO
    fun updateMember(id: Long, request: CreateMemberRequestDTO): MemberResponseDTO
    fun deleteMember(id: Long)
}