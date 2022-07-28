package hello.hellospring.domain;


public class Member {

    private Long id;
    private String name;

    //generate ... 에서 getter, setter 생성
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
