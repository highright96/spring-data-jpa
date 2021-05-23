package dev.highright96.springdatajpa.controller;

import dev.highright96.springdatajpa.dto.MemberDto;
import dev.highright96.springdatajpa.entity.Member;
import dev.highright96.springdatajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; ++i) {
            memberRepository.save(new Member("user" + i, i));
        }
    }

    @GetMapping("/member1/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 컨버터 : 권장하지 않는 방식 (복잡할 때에는 사용하지 못함)
    @GetMapping("/member2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<Member> list(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @GetMapping("/members/{age}")
    public Page<MemberDto> listByAge(@PathVariable("age") int age,
                                     @PageableDefault(size = 10, page = 1, sort = "age", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberRepository.findPageByAge(age, pageable).map(MemberDto::new);
    }
}

