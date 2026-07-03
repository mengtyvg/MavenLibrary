package com.example.librarymaven.controller

import com.example.librarymaven.dto.CreateMemberRequestDTO
import com.example.librarymaven.dto.MemberResponseDTO
import com.example.librarymaven.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/all")
    fun getAllMembers(): List<MemberResponseDTO> {
        return memberService.getAllMembers()
    }

    @GetMapping("/{id}")
    fun getMemberById(@PathVariable id: Long): MemberResponseDTO {
        return memberService.getMemberById(id)
    }

    @PostMapping("/create")
    fun createMember(
        @Valid @RequestBody request: CreateMemberRequestDTO
    ): MemberResponseDTO {
        return memberService.createMember(request)
    }

    @PutMapping("/update/{id}")
    fun updateMember(
        @PathVariable id: Long,
        @Valid @RequestBody request: CreateMemberRequestDTO
    ): MemberResponseDTO {
        return memberService.updateMember(id, request)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteMember(@PathVariable id: Long): String {
        memberService.deleteMember(id)
        return "member successfully deleted"
    }
}