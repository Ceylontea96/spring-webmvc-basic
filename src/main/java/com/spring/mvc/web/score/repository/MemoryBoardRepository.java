package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import com.spring.mvc.web.score.domain.Score;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Log4j2
public class MemoryBoardRepository implements BoardRepository {

    private static Map<Integer, Board> boardMap = new HashMap<>();

    //초기 데이터
    static {
        boardMap.put(1, new Board("1", "2", "3"));
        boardMap.put(2, new Board("qkqh", "가나다라마바사", "3"));
        boardMap.put(3, new Board("멍청이", "이렇고 저렇고", "3"));
    }
    @Override
    public void save(Board board) {
        boardMap.put(board.getBoardNo(), board);
        log.info(boardMap);
    }

    @Override
    public void remove(int bulNum) {
        boardMap.remove(bulNum);
    }

    @Override
    public Board findOne(int bulNum) {
        return boardMap.get(bulNum);
    }

    @Override
    public void change(int bulNum, DummyBoard dummyBoard) {
        Board board = boardMap.get(bulNum);
        board.setWriter(dummyBoard.getNewWriter());
        board.setTitle(dummyBoard.getNewTitle());
        board.setContent(dummyBoard.getNewContent());
    }

    @Override
    public List<Board> findAll() {
        List<Board> boardList = new ArrayList<>();

        for (int key : boardMap.keySet()) {
            boardList.add(boardMap.get(key));
        }

        return boardList;
    }
}
