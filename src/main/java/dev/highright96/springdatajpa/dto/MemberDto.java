package dev.highright96.springdatajpa.dto;

import dev.highright96.springdatajpa.entity.Member;
import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String username;

    public MemberDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
