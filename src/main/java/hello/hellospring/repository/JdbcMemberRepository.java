package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//이 방식으로 소스를 쓸 일은 없지만 (구식이라) 참고용으로!

public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource; //DB에 붙으려면 dataSource 필요함
    //(나중에 스프링을 통해 주입받아야됨)
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)"; //파라미터 바인딩?

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //결과 받음

        try {
            conn = getConnection(); //커넥션 가져오기
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //RETURN_GENERATED_KEYS -> sql 옵션

            pstmt.setString(1, member.getName()); //넘어오는거에 이름
            //파라미터인덱스 -> insert~value(?) 여기 ?랑 매칭됨 getName 으로 넘김

            pstmt.executeUpdate(); //db 의 실제 쿼리가 날라감
            rs = pstmt.getGeneratedKeys(); //위 RETURN_GENERATED_KEYS 와 매칭
            //db 의 id 넘버 꺼내줌

            if (rs.next()) { //rs 값이 있으면 꺼내옴
                member.setId(rs.getLong(1)); //꺼내와서 세팅
            } else {
                throw new SQLException("id 조회 실패");
            }

            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); //끝나면 자원들 릴리즈 (중요!!!!)
            //안하면 큰일납니다
        }
    }

    //조회
    @Override
    public Optional<Member> findById(Long id) {

        String sql = "select * from member where id = ?"; //가져옴

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id); //세팅

            rs = pstmt.executeQuery(); //여기는 앞 save 랑 다름

            if(rs.next()) { //값이 있으면 쭉 만들어서 반환
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
                
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            //여기도 닫아주기
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    //close 도 복잡하다고...
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //connection 은 닫을때도 DataSourceUtils 를 통해 닫아줘야 함
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
