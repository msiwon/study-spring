package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;
    //jpa 는 엔티티매니저로 모든걸 동작
    //아까 gradle 에서 추가한게 스프링 실행하면서 엔티티매니저를 만들어줌
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    //jpa 쓰려면 주의해야 될것... 항상 트랜잭션 써야함 -> MemberService 에 추가
    @Override
    public Member save(Member member) {
        em.persist(member); //persist 영구저장하다
        return member;
        //jpa 이용하면 전에 setId 일일히 해줬던거 까지 알아서 해줌...
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class,id); //조회 em.find
        //find(조회할 타입, 식별자값 PK)
        return Optional.ofNullable(member);
        //optional 로 감싸서 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        //findById 와 달리 특별한 jpql 이라는 객체지향 쿼리 써야함 (List 형태라)
        //findById 처럼 PK 기반 아니면 다 jpql 쿼리 작성해줘야함
        List<Member> result =
                em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }
    //그런데 스프링데이터 jpa 로 이것도 줄일 수 있다...
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                //jpql 쿼리 언어... 객체(엔티티)를 대상으로 쿼리를 날림 객체 자체를 select
                .getResultList();

    }
}
