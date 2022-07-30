package hello.hellospring.service;

//컴포넌트 스캔 사용하지 않고 자바 코드로 직접 스프링 빈 등록하기...의 클래스

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration //스프링 실행때...
public class SpringConfig {
/*
    @Bean //이 로직에 들어와서 스프링 빈에 인식시킴
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    중복안되게 일부러 주석처리함
*/
    //향후 메모리 레포지토리를 다른 레포지토리로 변경할 예정인 경우...
    //여기서만 바꾸면 돼서 쉽다 (이런 경우에 코드로 스프링 빈 설정이 편리)
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    //컨트롤러는 x
}

