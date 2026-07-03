package com.example.librarymaven.service.impl

import com.example.librarymaven.dto.CreateMemberRequestDTO
import com.example.librarymaven.dto.MemberResponseDTO
import com.example.librarymaven.entity.MemberEntity
import com.example.librarymaven.repository.MemberRepository
import com.example.librarymaven.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
) : MemberService {

    override fun getAllMembers(): List<MemberResponseDTO> {
        return memberRepository.findAll().map { toMemberResponseDTO(it) }
    }

    override fun getMemberById(id: Long): MemberResponseDTO {
        val member = memberRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found")
            }

        return toMemberResponseDTO(member)
    }

    override fun createMember(request: CreateMemberRequestDTO): MemberResponseDTO {
        val name = request.name?.trim()
        val email = request.email?.trim()
        val phone = request.phone?.trim()

        if (memberRepository.existsByEmail(email)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Member with this email already exists"
            )
        }

        val member = MemberEntity(
            name = name,
            email = email,
            phone = phone
        )

        val savedMember = memberRepository.save(member)

        return toMemberResponseDTO(savedMember)
    }

    override fun updateMember(id: Long, request: CreateMemberRequestDTO): MemberResponseDTO {
        val member = memberRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found")
            }

        val name = request.name?.trim()
        val email = request.email?.trim()
        val phone = request.phone?.trim()

        if (memberRepository.existsByEmailAndIdNot(email, id)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Member with this email already exists"
            )
        }

        member.name = name
        member.email = email
        member.phone = phone
        member.updatedAt = LocalDateTime.now()

        val updatedMember = memberRepository.save(member)

        return toMemberResponseDTO(updatedMember)
    }

    override fun deleteMember(id: Long) {
        if (!memberRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found")
        }

        memberRepository.deleteById(id)
    }


        // convert entity to DTO
    private fun toMemberResponseDTO(member: MemberEntity): MemberResponseDTO {
        return MemberResponseDTO(
            id = member.id,
            name = member.name,
            email = member.email,
            phone = member.phone,
            status = member.status
        )
    }
}