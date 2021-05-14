package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcBoardRepositoryTest {

    @Autowired
    @Qualifier("jbr")
    private BoardRepository repository;

    @Test
    void saveTest() {
        Board board = new Board();
        board.setWriter("도우너");
        board.setTitle("코스모스");
        board.setContent("깐따삐야!");
        board.setRecommend(0);
        board.setViews(0);
//        board.setPostTime(LocalDateTime.now());
        repository.save(board);
    }

    @Test
    void removeTest() {
        repository.remove(10);
    }

    @Test
    void findAllTest() {
        for (Board b : repository.findAll()) {
            System.out.println(b);
        }
    }

    @Test
    void findOneTest() {
        System.out.println(repository.findOne(11));
    }

    @Test
    void changeTest() {
        DummyBoard dummyBoard = new DummyBoard("고길동", "더이상 이렇게는 못살아", "여긴 내집이야");
        repository.change(12, dummyBoard);
    }
}