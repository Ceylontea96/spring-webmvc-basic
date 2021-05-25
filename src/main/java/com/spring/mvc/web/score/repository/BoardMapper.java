package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.common.paging.Criteria;
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
    // 1. 페이징 없는 버전
    List<Board> findAll();

    // 2. 페이징 쿼리 추가 버전
    List<Board> findAll(Criteria criteria);

    // 3. 검색 쿼리 추가버전
    List<Board> getSearchArticles(Criteria criteria);

    //총 게시물 수 조회
    int getTotalCount(Criteria criteria);

    //게시글 추천 기능
    void plusRec(int bulNum);

    //조회수 증가 기능
    void plusViews(int bulNum);

}
