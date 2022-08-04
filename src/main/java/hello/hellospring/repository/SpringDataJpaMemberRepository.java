package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//우선 jpa 레포지토리 받아야 함
public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>, MemberRepository{
    //멤버에서 pk 인 id 를 가져오는데 id 타입이 long 이라 / 인터페이스는 다중상속 가능
    //이건 따로 bean 할 필요 없이 스프링이 알아서 등록해준다 -> 그냥 가져다가 쓰면 됨

    //상속받은 JpaRepository 안에 많이 만들어져 있음 기본적인 것들 모두에게 통용 (공통클래스)
    //그런데 따로 만들어줘야 하는것도 있음 밑 findByName 이 예시
    //name 이 아니라 이메일일 수도 있고... 비즈니스마다 달라 공통이 아니기 때문에 이런건 따로 작성
    @Override
    Optional<Member> findByName(String name);
    //규칙 : findByName -> JPOL select m from Member m where m.name = ? 이렇게 짜준다
    //ex) findByNameAndId(String name, Long id) 이런것도 있다 등등... 많음
    //인터페이스 이름만으로도 끝낼 수 있다...

}
