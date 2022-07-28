package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
//처음 implements 하면 alt + enter 해서 한번에 생성할 수 있다 (밑에 override 들)
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //키값을 생성해줌
    //실무에선 동시성 문제가 있을 수 있음 예제라서 단순하게 쓰는것

    public void clearStore(){
        store.clear();
    }

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //store 에 넣기 전에 id 값 세팅
        store.put(member.getId(), member); //store (map) 에 저장
        return member; //저장 결과 반환
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //결과가 없으면 null 나온다 -> Optional 로 null 이여도 감싸서 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        //자바 람다식 이용
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                //member.getName 이 파라미터로 넘어온 name 과 같은지 판단
                //(같은 경우에만 반환 filter)
                .findAny();
    }

    @Override
    public List<Member> findAll() { //리턴값은 map 이 아닌 list (실무에서 더 편하다고)
        return new ArrayList<>(store.values());
    }

}
//동작 하는지 안하는지 판단하기 위해 test case 작성 !
