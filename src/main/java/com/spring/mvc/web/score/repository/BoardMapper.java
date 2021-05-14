package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 게시글 저장 기능
    void save(Board board);

    // 게시글 삭제 기능
    void remove(int bulNum);

    // 게시글 조회 기능
    Board findOne(int bulNum);

    // 게시글 수정 기능
    void change(DummyBoard dummyBoard);

    // 전체 게시글 조회 기능
    List<Board> findAll();

    //게시글 추천 기능
    void plusRec(int bulNum);

    //조회수 증가 기능
    void plusViews(int bulNum);

}
