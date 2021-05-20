package dev.highright96.springdatajpa.repository;

import dev.highright96.springdatajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void testMember() {
        Member member = new Member("memberA", 10, null);

        Member first = memberJpaRepository.save(member);
        Member second = memberJpaRepository.find(first.getId());

        Assertions.assertThat(first.getId()).isEqualTo(second.getId());
        Assertions.assertThat(first.getUsername()).isEqualTo(second.getUsername());
    }

}