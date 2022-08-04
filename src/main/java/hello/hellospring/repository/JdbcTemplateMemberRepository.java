package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//JdbcTemplate 반복코드를 제거해줌 but SQL 은 직접 작성해줘야함 -> JPA 로 보완
//repository 만들때 memberRepository 인터페이스 만들어둔거 쓰면 된다 (잊지마!)

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate; //이거 써주면 됩니다

    @Autowired //밑처럼 생성자가 딱 하나일 경우 Autowired 생략 가능
    public JdbcTemplateMemberRepository(DataSource dataSource) { //datasource 로 고침
        jdbcTemplate = new JdbcTemplate(dataSource); //dataSource 넣어줌
    }//이렇게 해주면 됩니다

    @Override
    public Member save(Member member) { //이렇게...

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(),id);
        //result 에 매핑된 결과 받음 (밑 RowMapper 에서 받은 것)
        return result.stream().findAny();//list 로 받았기 때문에 stream 으로 반환해서 return
        //순수 jdbc 와 비교하면 어마어마하게 단축.. 단 두줄로 단축 가능!
    }


    @Override
    public Optional<Member> findByName(String name) {
        //findById 에서 id 만 name 으로 바꿔줌
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
        //all 은 따로 변환 필요 없이 객체 생성받은 값 그대로 리턴
    }


    //매핑
    private RowMapper<Member> memberRowMapper(){
        /*return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                //이거 설정해서 return...
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName((rs.getString("name")));
                return member;
            }
        }*/
        //위에 코드 람다식으로 바꾼 것 (alt+enter 사용)
        return (rs, rowNum) -> {
            //이거 설정해서 return...
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName((rs.getString("name")));
            return member;
        };
    }
}
//이거 만들었으면 SpringConfig 에 새로 join 해주기
//(기존거에서 갈아치움)

