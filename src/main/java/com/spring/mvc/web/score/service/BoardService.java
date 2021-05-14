package com.spring.mvc.web.score.service;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import com.spring.mvc.web.score.repository.BoardMapper;
import com.spring.mvc.web.score.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

//    private final BoardRepository boardRepository;
    private final BoardMapper boardRepository;

//    @Autowired
//    public BoardService(@Qualifier("jbr") BoardRepository boardRepository) {
//        this.boardRepository = boardRepository;
//    }

    // 게시글 저장 기능
    public void register(Board board) {
        boardRepository.save(board);
    }

    // 게시글 목록을 받아오는 기능 (내림차순)
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    // 게시글 삭제 기능
    public void delete(int bulNum) {
        boardRepository.remove(bulNum);
    }

    // 게시글 수정 기능
    public void modify(int bulNum, DummyBoard dummyBoard) {
        dummyBoard.setBulNum(bulNum);
        boardRepository.change(dummyBoard);
    }

    // 게시글 조회 기능
    public Board viewDetail(int bulNum, boolean viewFlag) {
        if (viewFlag) boardRepository.plusViews(bulNum);//전달받은 viewFlag값이 true면 조회수를 1증가시킴
        return boardRepository.findOne(bulNum);
    }

    //게시글 추천 기능
    public void plusRecommend(int bulNum) {
        boardRepository.plusRec(bulNum);
    }
}
