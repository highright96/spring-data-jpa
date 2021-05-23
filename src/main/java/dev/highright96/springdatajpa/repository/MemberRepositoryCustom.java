package dev.highright96.springdatajpa.repository;

import dev.highright96.springdatajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
