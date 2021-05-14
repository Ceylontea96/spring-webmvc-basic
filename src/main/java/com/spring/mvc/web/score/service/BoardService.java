package com.spring.mvc.web.score.service;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import com.spring.mvc.web.score.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(@Qualifier("jbr") BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 저장 기능
    public void register(Board board) {
        boardRepository.save(board);
    }

    // 게시글 목록을 받아오는 기능 (내림차순)
    public List<Board> getBoardList() {
        List<Board> origianlList = boardRepository.findAll();
        List<Board> temp = new ArrayList<>();

        for (int i = origianlList.size()-1; i >= 0 ; i--) {
            temp.add(origianlList.get(i));
        }
        origianlList = temp;
        return origianlList;
//        return boardRepository.findAll();
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
