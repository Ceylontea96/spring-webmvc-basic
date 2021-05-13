package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.score.domain.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("jr")
@RequiredArgsConstructor//final 생성자를 대신 생성해줌
public class JdbcScoreRepository implements ScoreRepository{

    //스프링 JDBC가 제공하는 jdbc 템플릿
    private final JdbcTemplate template;

    /*
    @Autowired
    public JdbcScoreRepository(JdbcTemplate template) {
        this.template = template;
    }
    위의 생성자를 생략가능(RequiredArgsConstructor)*/

    @Override
    public void save(Score score) {
        String sql = "INSERT INTO score VALUES " +
                "(seq_score.nextval, ?, ?, ?, ?, ?, ?)";
        //번호(시퀀스 이용), 이름, 국어, 영어, 수학, 총점, 평균
        template.update(sql, score.getName(), score.getKor(), score.getEng()
                , score.getMath(), score.getTotal(), score.getAverage());
    }

    @Override
    public void remove(int stuNum) {
        String sql = "DELETE FROM score WHERE stu_num = ?";
        template.update(sql, stuNum);
    }

    @Override
    public Score findOne(int stuNum) {
        String sql = "SELECT * FROM score WHERE stu_num = ?";
        return template.queryForObject(sql, new ScoreMapper(), stuNum);
    }

    @Override
    public List<Score> findAll() {
        String sql = "SELECT * FROM score";
        return template.query(sql, new ScoreMapper());
    }

    //내부 클래스 (inner class)
    class ScoreMapper implements RowMapper<Score> {


        @Override
        public Score mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Score(rs);
        }
    }

}
