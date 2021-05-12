package com.spring.mvc.web.score.service;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import com.spring.mvc.web.score.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 저장 기능
    public void register(Board board) {
        boardRepository.save(board);
    }

    // 게시글 목록을 받아오는 기능
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    // 게시글 삭제 기능
    public void delete(int bulNum) {
        boardRepository.remove(bulNum);
    }

    // 게시글 수정 기능
    public void modify(int bulNum, DummyBoard dummyBoard) {
        boardRepository.change(bulNum, dummyBoard);
    }

    // 게시글 조회 기능
    public Board viewDetail(int bulNum) {
        return boardRepository.findOne(bulNum);
    }
}
