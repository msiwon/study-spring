package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping("hello-mvc")
    //외부에서 파라미터를 받음 required의 기본값은 true. 안바꿔주면 에러남
    //name을 넘길 때 localhost8080/hello-mvd다음?name=으로 넘겨줌
    public String helloMvc(@RequestParam(value = "name", required = false) String name,Model model){
        model.addAttribute("name",name);//키,네임
        return "hello-template";//hello-template로 전달 (템플릿 하위 파일요)
    }
    @GetMapping("hello-string")//새 방식
    @ResponseBody //중요!의미는 http의 바디부에 이 데이터를 직접 넣어주겠다는 의미
    public String helloString(@RequestParam("name")String name){
        return "hello " + name;//템플릿엔진(위에서 쓴 방식)과의 차이
        //뷰 이런게 없음 그냥 이 문자 그대로 올라감
        //페이지 소스 보기 보면 위 방식과는 다르게(html 코드들 없이) 딱 이 한문장만이 있음
    }
    //진짜는 지금부터 -> 문자 말고 데이터를 내놓아라 하면
    @GetMapping("hello-api")
    @ResponseBody //이게 붙어있으면 뷰 리졸버에 던져지지 않고 http컨버터로 던져짐
    //문자면 바로 넘어감 (위에 것) http 컨버터에서 string 컨버터로 분류 (기본 문자처리)
    //객체가 있다면 json 방식으로 넘겨짐 http 컨버터에서 json 컨버터로 분류 (기본 객체처리)
    public Hello helloApi(@RequestParam("name")String name){
        Hello hello = new Hello();
        hello.setName((name));
        return hello;
    }
    static class Hello{ //json방식...
        public String name;
        //게터세터
        public String getName() {
            return name;
        }
        //왼 마우스 > Generate... > 찾으면 됨 프로퍼티 접근 방식

        public void setName(String name) {
            this.name = name;
        }
    }


}
