package hello.hellospring.domain;

import javax.persistence.*;

//jpa 는 표준인터페이스 ..기업별로 구현에서 쓴다고
//jpa 는 매핑 필요 -> @Entity (jpa 가 관리한다)
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY 로 설정하면 db 가 알아서 생성해주는
    private Long id;
    //@Column(name="username") //db의 colum 명 username 으로 매핑됨
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
