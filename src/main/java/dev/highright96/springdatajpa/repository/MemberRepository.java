package dev.highright96.springdatajpa.repository;

import dev.highright96.springdatajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3By();

    // 생략 가능 @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

}
