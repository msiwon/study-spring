package hello.hellospring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/") //이 슬래시... 8080 들어가면 바로 밑 호출
    public String home(){
        return "home";
    }
    //template 에 home 추가해줌여 (실행이 아니고 내가 추가했단 뜻
}
