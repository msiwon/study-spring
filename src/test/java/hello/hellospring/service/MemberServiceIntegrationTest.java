package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// (기존 MemberServiceTest 에서 소스 가져옴 더 자세한 설명은 거기서)
//db랑 통합해서 테스트 ...통합테스트
//그런데 통합테스트보다 단위테스트가 더 좋고(그럴 확률이 높다) 그걸 잘 쓰기 위해
//컨테이너 없이 테스트를 할 수 있는 연습을 하는것이 좋다

//밑 두개 적어주기 @@

@SpringBootTest
@Transactional //이건 왜? -> 안쓰면 테스트할때마다 db에 저장이 된다
    //테스트가 끝나면 항상 롤백 -> 테스트한 데이터가 db에 반영되지 않음 (다음 테스트에 영향x)
    //testcase 에서만 롤백하는것임
class MemberServiceIntegrationTest {

    //테스트는 어차피 끝부분... 편리하게 사용
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    //AfterEach 랑 BeforeEach 가 필요가 없어짐

    @Test
    void 회원가입(){

        //given:이런 상황이 주어져서
        Member member = new Member();
        member.setName("hello");

        //when:이걸 실행했을때
        Long saveId = memberService.join(member); //조인

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }


    @Test
    public void 중복_회원_예외() {

        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //memberService.join(member2); 중복 상황

        IllegalStateException e =
                assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); //람다
        //memberService.join(member2) 를 할때 IllegalStateException.class 가 터져야 함
        //(예외가 발생해야 함)
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");



    }

}