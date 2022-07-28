package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

//인터페이스로 설정 -> 아직 데이터 저장소가 선정되지 않아서
//우선 인터페이스로 구현 클래스를 변결할 수 있도록 설계 (임시
public interface MemberRepository {
    Member save(Member member);
    //Optional 은 가져오는 값이 null 일수도 있는걸 그대로 반환보다 감싸서 반환
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
