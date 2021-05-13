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
        boardMap.put(1, new Board("둘리", "아이~씻팔!!!", "초능력 맛 좀 볼래?"));
        boardMap.put(2, new Board("도우너", "떨 한대 할래?", "한대 말아줄까?"));
        boardMap.put(3, new Board("고길동", "이렇게는 못살아!!!", "여긴 내 돈 주고 산 내 집이야!!!"));
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
