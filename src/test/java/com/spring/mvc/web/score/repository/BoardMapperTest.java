package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.common.paging.Criteria;
import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @Test
    void saveTest() {
        Board board = new Board();
        board.setWriter("마이콜");
        board.setTitle("호의가 계속되면 권리인줄 아는");
        board.setContent("못생긴 공룡녀석");
        boardMapper.save(board);
    }

    @Test
    void removeTest() {
        boardMapper.remove(4);
    }

    @Test
    void findOneTest() {
        Board one = boardMapper.findOne(5);
        System.out.println("one = " + one);
    }

    @Test
    void findAllTest() {
        List<Board> list = boardMapper.findAll();
        for (Board board : list) {
            System.out.println(board);
        }
    }

    @Test
    void changeTest() {
        DummyBoard db = new DummyBoard("홍길동", "호형호제", "하지 못하다니");

        db.setBulNum(6);
        boardMapper.change(db);
    }

    @Test
    void recommendTest() {
        boardMapper.plusRec(5);
    }

    @Test
    @DisplayName("300개의 게시글을 등록해야 한다.")
    void bulkInsert() {
        for (int i = 0; i < 300; i++) {
            Board board = new Board();
            board.setTitle("테스트 제목" + i);
            board.setContent("테스트 내용입니다" + i);
            board.setWriter("USER" + i);

            boardMapper.save(board);
        }
    }

    @Test
    @DisplayName("페이지 정보에 따른 게시물을 조회해야 한다.")
    void pagingTest1() {
        System.out.println("=======================================");
        Criteria criteria = new Criteria(1, 30);
        for (Board board : boardMapper.findAll(criteria)) {
            System.out.println(board);
        }
        System.out.println("=======================================");
    }












}