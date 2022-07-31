package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //클래스 생성하자마자
public class MemberController {
    private final MemberService memberService; //하나만 등록
    //연결 Generate-constructor 그리고
    @Autowired //멤버서비스를 가져다가 연결시켜줌 -> 의존관계 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //생성자 주입 방법이 최고

    //회원 웹 기능 - 등록
    @GetMapping("/members/new") //home 페이지 참고
    public String createForm(){
        return "members/createMemberForm";
        //template 의 directory 폴더인 members 의
        //create html 로 이동
    }
    //위아래 둘 다 /members/new 를 mapping 하지만 get 과 post 의 차이가 있음
    //보통 조회엔 get 등록엔 post 사용
    @PostMapping("/members/new")//얘는 PostMapping
    //보통 form 태그를 통해 전달될때 Post .. createMemberForm 페이지 참고
    public String create(MemberForm form){ //MemberForm 에 입력받은 name 값이 들어감
        Member member = new Member();
        member.setName(form.getName()); //member 받아옴

        memberService.join(member); //memberService 로 member 넘김 (가입)
        return "redirect:/"; //회원가입이 끝나면 home 으로 redirect ..보냄
    }

    //회원 웹 기능 - 조회
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        //findMembers 로 다 꺼내오기
        model.addAttribute("members",members);
        //members 리스트 전부 members 를 키값으로 넘김 -> memberList 에서 ${} 으로 랜더링
        return "members/memberList";
    }
}
