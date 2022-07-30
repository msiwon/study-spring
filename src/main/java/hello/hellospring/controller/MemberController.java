package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller //클래스 생성하자마자
public class MemberController {
    private final MemberService memberService; //하나만 등록
    //연결 Generate-constructor 그리고
    @Autowired //멤버서비스를 가져다가 연결시켜줌 -> 의존관계 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //생성자 주입 방법이 최고
}
