package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*; //이거 그냥 수동 추가함..
public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //test 메소드들이 동작 순서가 정해진 것이 아니기 때문에 (의존관계 없이 설계)
    //test 가 하나 끝나면 공용 data 를 깔끔하게 clear 해줘야 문제가 없다
    //AfterEach -> 하나 끝날때마다 실행
    @AfterEach
    public void afterEach() {
        repository.clearStore(); //clearStore 실행하여 비워줌
    }

    //test 실행했을때 오류(빨간불) 없이 진행되면 통과
    @Test
    public void save(){ //아까 만든 save 기능을 테스트
        //추천하는 test 문법 : given-when-then
        //given
        Member member = new Member();
        member.setName("spring"); //이름 임시로 설정
        //when
        repository.save(member);
        //then
        Member result = repository.findById(member.getId()).get();
        //반환 타입이 optional 이라 get 으로 꺼냄 (좋은 방법은 아님)
        assertThat(result).isEqualTo(member); //Assertions
        //가져온 result 와 member 가 같으면 ok (기대값, 실제값)

    }
    @Test
    public void findByName() { 
        //given
        Member member1 = new Member();
        member1.setName("spring1"); 
        repository.save(member1); //저장

        //정교한 테스트를 위해 하나 더 생성
        //복붙한.. 이름 한번에 변경 팁 : 선택해서 shift + f6
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        
        //when
        Member result = repository.findByName("spring1").get(); //get 으로 꺼내옴
        
        //then
        assertThat(result).isEqualTo(member1);
        //만약 member2 라면 같지 않아 에러 발생
    }
    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        //size 측정 3으로 바꾸면 에러
    }

}
