package hello.hellospring.service;

//컴포넌트 스캔 사용하지 않고 자바 코드로 직접 스프링 빈 등록하기...의 클래스
import hello.hellospring.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //스프링 실행때...
public class SpringConfig {

    private final MemberRepository memberRepository;
    @Autowired//생성자 하나면 생략 가능...하지만 일단 씀
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    //jpa 쓸때 필요했던 것
   /* private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }
    */
    //repository jdbc template 쓸 때 필요했던 것
    /*db 전달을 위한...
    private DataSource dataSource; 이 다음 constructor 하고 @Autowired
    스프링이 자체적으로 만들어줌... 이걸 밑 db 레포지토리로

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @Bean //이 로직에 들어와서 스프링 빈에 인식시킴
    //public MemberService memberService(){return new MemberService(memberRepository());} //스프링데이터 jpa 이전
    public MemberService memberService(){
        return new MemberService(memberRepository);
    } //의존관계 새로 세팅


    //향후 메모리 레포지토리를 다른 레포지토리로 변경할 예정인 경우...
    //여기서만 바꾸면 돼서 쉽다 (이런 경우에 코드로 스프링 빈 설정이 편리)
/*    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository(); 원래 이걸로 임시로 썼는데
        //return new JdbcMemberRepository(dataSource);//db 저장소 만들고 바꿔줌 순수 jdbc
        //위에 쓴 대로 다른 레포지토리로 쉽게 변경
        //return new JdbcTemplateMemberRepository(dataSource);
        //return new JpaMemberRepository(em);

    }스프링 데이터 jpa 쓰니까 순식간에...
*/
    //컨트롤러는 x
}

