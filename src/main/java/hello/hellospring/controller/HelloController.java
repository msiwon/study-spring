package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //컨트롤러는 @컨트롤 필요
public class HelloController {
    @GetMapping("hello") //이 메소드 실행
    public String hello(Model model){ //모델 만들어 데이터 넣어줌
        model.addAttribute("data", "hello!!");
        model.addAttribute("imsi", "실험해보기");
        return "hello"; //templates의 hello.html과 연결
        //컨트롤러에서 리턴값으로 문자를 반환하면 뷰 리졸버가 화면을 찾아 처리한다.
        //스프링 부트 템플릿엔진 기본 viewName 매핑 (기본세팅)
        //'resources(폴더):templates/'+{ViewName}+'.html'
    }

}
