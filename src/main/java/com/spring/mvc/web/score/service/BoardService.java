package com.spring.mvc.web.score.service;

import com.spring.mvc.web.common.paging.Criteria;
import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import com.spring.mvc.web.score.repository.BoardMapper;
import com.spring.mvc.web.score.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional//트랜잭션 처리 자동화
    public void register(Board board) {
        boardRepository.save(board);

        //만약에 첨부파일이 존재한다면 추가 쿼리를 동작해야 함
        List<String> filePathList = board.getFilePathList();
        if (filePathList != null) {
            for (String path : filePathList) {
                boardRepository.addFile(path);
            }
        }
    }

    // 게시글 목록을 받아오는 기능 (내림차순)
    public List<Board> getBoardList(Criteria criteria) {
//        return boardRepository.findAll(criteria);
        List<Board> articles = boardRepository.getSearchArticles(criteria);

        //3분 이내 신규글 new카ㅡ 붙이기
        for (Board article : articles) {
            //각 게시물들의 등록시간 읽어오기 (밀리초)
            long regTime = article.getRegDate().getTime();//getTime을 쓰면 시간을 밀리초로 가져옴
            //현재시간 읽어오기 (밀리초)
            long now = System.currentTimeMillis();
            //3분이내(밀리초)면 신규게시글
            if (now - regTime < 60 * 3 * 1000) {
                article.setNewArticle(true);
            }

        }
        return articles;
        //페이징, 검색 쿼리 적용버전
    }

    //총 게시물 수 확인
    public int getTotal(Criteria criteria) {
        return boardRepository.getTotalCount(criteria);
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
    @Transactional//트랜잭션 처리
    public Board viewDetail(int bulNum, boolean viewFlag) {
        Board content = boardRepository.findOne(bulNum);

        if (viewFlag) boardRepository.plusViews(bulNum);//전달받은 viewFlag값이 true면 조회수를 1증가시킴
        return content;
    }

    //첨부파일 경로목록 구하기
    public List<String> getFilePaths(int bulNum) {
        return boardRepository.getFilePath(bulNum);
    }

    //게시글 추천 기능
    public void plusRecommend(int bulNum) {
        boardRepository.plusRec(bulNum);
    }
}
