package dev.highright96.springdatajpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final EntityManager em;

    // 화면 맞춤 쿼리, 라이프 사이클이 다른, 핵심 쿼리가 아닌 부분은 따로 분리하는 것이 좋다.

}
