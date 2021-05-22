package dev.highright96.springdatajpa.repository;

import dev.highright96.springdatajpa.dto.MemberDto;
import dev.highright96.springdatajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testMember() {
        Member member = new Member("memberA", 10, null);

        Member first = memberRepository.save(member);
        Member second = memberRepository.findById(first.getId()).get();

        assertThat(first.getId()).isEqualTo(second.getId());
        assertThat(first.getUsername()).isEqualTo(second.getUsername());
        assertThat(first).isEqualTo(second);
    }

    @Test
    void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void methodTest() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        Member member3 = new Member("member3");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> top3By = memberRepository.findTop3By();
        assertThat(3).isEqualTo(top3By.size());
    }

    @Test
    void namedQuery() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMember = memberRepository.findByUsername("member1");
        assertThat(findMember.get(0)).isEqualTo(member1);
    }

    @Test
    void query() {
        Member member1 = new Member("member1", 10);

        memberRepository.save(member1);

        List<Member> findMember = memberRepository.findUser("member1", 10);
        assertThat(findMember.get(0)).isEqualTo(member1);
    }

    @Test
    void findUsernameList() {
        Member member1 = new Member("member1", 10);

        memberRepository.save(member1);

        List<String> findName = memberRepository.findUsernameList();
        assertThat(findName.get(0)).isEqualTo("member1");
    }

    @Test
    void findMemberDto() {

        Member member1 = new Member("member1", 10);

        memberRepository.save(member1);

        List<MemberDto> memberDtos = memberRepository.findMemberDto();
        assertThat(memberDtos.get(0).getUsername()).isEqualTo(member1.getUsername());
    }

    @Test
    void findByNames() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        Member member3 = new Member("member3");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> findMembers = memberRepository.findByNames(Arrays.asList("member1", "member2"));
        assertThat(findMembers.get(0).getUsername()).isEqualTo(member1.getUsername());
        assertThat(findMembers.get(1).getUsername()).isEqualTo(member2.getUsername());
    }

    @Test
    void returnType() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // NullPointException
        Member findMember = memberRepository.findMemberByUsername("asdasd");
        System.out.println("findMember = " + findMember.getUsername());
    }

    @Test
    public void paging() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> members = memberRepository.findPageByAge(age, pageRequest);
        // 엔티티 -> DTO 로 변환 필수!
        Page<MemberDto> dtoPage = members.map(m -> new MemberDto(m.getId(), m.getUsername()));

        //then
        List<Member> content = members.getContent();

        assertThat(content.size()).isEqualTo(3);
        assertThat(members.getTotalElements()).isEqualTo(5);
        assertThat(members.getNumber()).isEqualTo(0);
        assertThat(members.getTotalPages()).isEqualTo(2);
        assertThat(members.isFirst()).isTrue();
        assertThat(members.hasNext()).isTrue();
    }
}