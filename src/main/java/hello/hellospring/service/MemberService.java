package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service //스프링에게 인식시켜주기위해 -> 컴포넌트 스캔 (스트링 빈 생성)
/*
컴포넌트 스캔 말고 직접 등록도 할 수 있다 - SpringConfig 으로...
*/
@Transactional //jpa 위해 설정
public class MemberService {
    //검증을 위한 test case 자동 생성 (껍데기) -> ctrl + shift + T

    //회원 서비스를 관리하기 위한 회원 repository 설정
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //원래 위처럼 repository 를 따로 만들었는데 한 인스턴스로 사용하기위해 바꿈
    private final MemberRepository memberRepository;
    //@Autowired //의존관계 주입시켜줌 (연결)
    public MemberService(MemberRepository memberRepository) {
        //외부에서 repository 를 받아오도록 설정
        this.memberRepository = memberRepository;
    }

    //service 는 메소드명을 사무적으로 작성 (알아보기 쉽게) 비즈니스를 처리

    /**
     * 회원가입
     */
    public Long join(Member member) {

        validateDuplicateMember(member);//중복 회원 검증
        memberRepository.save(member);//save 에 저장
        return member.getId();//id 반환

    }

    //같은 이름은 안된다는 룰 지정했을 때 를 가정하여 만든
    private void validateDuplicateMember(Member member) {
        /* 이렇게 나눠서 쓸 수 있는데 optional 로 바로 반환받는거 바람직하지 않음
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.isPresent(m -> { throw new IllegalStateException("이미 존재하는 회원입니다.");});
        */
        //이렇게 바로 써주기
        memberRepository.findByName(member.getName())
                //refactor this -> extract method 선택해 메소드로 뽑아냄
                //(원래는 메소드가 아니라 그냥 join 에 들어가 있었는데 빼준것
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        //중복 이름이 있으면 반환
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() { //전체 찾기
        return memberRepository.findAll();
    }
    
    public Optional<Member> findOne(Long memberId) { //하나 찾기
        return memberRepository.findById(memberId);
    }
}




