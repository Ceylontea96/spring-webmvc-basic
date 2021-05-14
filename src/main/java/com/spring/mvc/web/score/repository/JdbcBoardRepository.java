package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("jbr")
@RequiredArgsConstructor
public class JdbcBoardRepository implements BoardRepository{

    //스프링 JDBC가 제공하는 jdbc 템플릿
    private final JdbcTemplate template;

    @Override
    public void save(Board board) {
        String sql = "INSERT INTO board VALUES " +
                "(seq_board.nextval, ?, ?, ?, ?, ?, ?)";
        //번호(시퀀스 이용), 작성자, 제목, 내용, 추천, 조회수
        template.update(sql, board.getWriter(), board.getTitle(), board.getContent()
                , board.getRecommend(), board.getViews(), Timestamp.valueOf(board.getPostTime()) );
    }

    @Override
    public void remove(int bulNum) {
        String sql = "DELETE FROM board WHERE board_no = ?";
        template.update(sql, bulNum);
    }

    @Override
    public Board findOne(int bulNum) {
        String sql = "SELECT * FROM board WHERE board_no = ?";
        return template.queryForObject(sql, new BoardMapper(), bulNum);
    }

    @Override
    public void change(int bulNum, DummyBoard dummyBoard) {
        String sql = "UPDATE board SET writer = ?, title = ?, content = ? WHERE board_no = ?";
        template.update(sql, dummyBoard.getNewWriter(), dummyBoard.getNewTitle(), dummyBoard.getNewContent(), bulNum);
    }

    @Override
    public List<Board> findAll() {
        String sql = "SELECT * FROM board";
        return template.query(sql, new BoardMapper());
    }

    //내부 클래스
    class BoardMapper implements RowMapper<Board> {

        @Override
        public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Board(rs);
        }
    }
}
