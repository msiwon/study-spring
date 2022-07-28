package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MemberServiceTest {

    //메모리 레퍼지토리를 한 인스턴스로 (같은 인스턴스로) 쓰게 바꾸기 ..서비스클래스로
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    //after 와 반대로 전마다 실행
    @BeforeEach
    public void beforeEach() {
        //테스트는 독립적으로 실행.. 테스트마다 만들어줌
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        //같은 repository 사용 (di)
    }


    @AfterEach
    public void afterEach() { //돌때마다 메모리 클리어
        memberRepository.clearStore();
    }

    //test 는 메소드명 과감하게 한글로 바꿔도 ok (알아보기 쉬움)
    //빌드될때 testcase 는 빠짐
    @Test
    public void 회원가입() throws Exception {
        //추천하시는 문법 given -when-then
        //given:이런 상황이 주어져서
        Member member = new Member();
        member.setName("hello");

        //when:이걸 실행했을때
        Long saveId = memberService.join(member);

        //then:이게 돼야됨 (검증)
        Member findMember = memberRepository.findById(saveId).get();
        //우리가 저장한게 repository 에 있는게 맞나? 를
        assertEquals(member.getName(), findMember.getName());
        //member 의 이름이 findMember 의 이름과 같은지
    }

    //테스트는 정상플로우도 중요하지만 오류플로도 중요! -> 예외도 검증해봐야

    @Test
    public void 중복_회원_예외() {

        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring"); //일부러 중복으로 (예외 확인)

        //when
        memberService.join(member1);
        //memberService.join(member2); 중복 상황

        IllegalStateException e =
                assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); //람다
        //memberService.join(member2) 를 할때 IllegalStateException.class 가 터져야 함
        //(예외가 발생해야 함)
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /* try-catch 문으로도 작성 가능 근데 애매.. 좋은 문법 씁시다
        try{
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
         */

    }

}