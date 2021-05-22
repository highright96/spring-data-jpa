package dev.highright96.springdatajpa.repository;

import dev.highright96.springdatajpa.dto.MemberDto;
import dev.highright96.springdatajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3By();

    // 생략 가능 @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    // 컴파일시 문법 에러를 확인할 수 있음
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new dev.highright96.springdatajpa.dto.MemberDto(m.id, m.username) " +
            "from Member m")
    List<MemberDto> findMemberDto();

    // 컬렉션 타입 파라미터
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    // 반환타입
    List<Member> findListByUsername(String username); // 리스트

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // 단건

    @Query(value = "select m from Member m left join m.team t"
            , countQuery = "select count(m) from Member m")
    Page<Member> findPageByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);
}
