package hello.hellospring.controller;


//실제 멤버 컨트롤러에도 추가를
public class MemberForm {
    private String name;
    //이 name 과 createMemberForm 의 name 과 매칭되며 값 들어옴

    public String getName() { //저장한 name 값 꺼내기
        return name;
    }

    public void setName(String name) { //받은 name 값으로 설정
        this.name = name;
    }
}
