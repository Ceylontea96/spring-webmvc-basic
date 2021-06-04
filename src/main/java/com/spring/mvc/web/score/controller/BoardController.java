package com.spring.mvc.web.score.controller;

import com.spring.mvc.web.common.paging.Criteria;
import com.spring.mvc.web.common.paging.PageMaker;
import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import com.spring.mvc.web.score.service.BoardService;
//import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    //게시글 목록화면 요청
    @GetMapping("/list")
    public String list(@ModelAttribute("cri") Criteria criteria, Model model) {
        log.info("get amount : " + criteria);
        model.addAttribute("boardList", boardService.getBoardList(criteria));
        //페이지 정보 만들어서 jsp에게 보내기
        model.addAttribute("pageMaker", new PageMaker(criteria, boardService.getTotal(criteria)));

        return "board/list";
    }

    //게시글 작성화면 요청
    @GetMapping("/register")
    public String list(HttpSession session) {


        return "board/register";
    }

    //게시글 정보 저장 요청
    @PostMapping("/register")
    public String register(Board board) {
        boardService.register(board);

        return "redirect:/board/list";
    }

    //게시글 삭제 요청
    @GetMapping("/delete")
    public String delete(int bulNum) {
        boardService.delete(bulNum);
        return "redirect:/board/list";
    }

    //게시글 상세 보기 요청
    @GetMapping("/detail")
    public String detail(int bulNum
            , @RequestParam("vf") boolean viewCntFlag
            , @ModelAttribute("cri") Criteria criteria//요청시에 바로 Model에 받아서 보낼 수 있음
            , Model model
    ) {
        Board board = boardService.viewDetail(bulNum, viewCntFlag);
        model.addAttribute("board", board);
//        model.addAttribute("cri", criteria);
        return "board/detail";
    }

    //게시글 수정 화면 요청
    @GetMapping("/modify")
    public String modify(int bulNum
            , @RequestParam("vf") boolean viewCntFlag
            , Model model
            , HttpSession session
    ) {
        Board board = boardService.viewDetail(bulNum, viewCntFlag);
        model.addAttribute("board2", board);
        return "board/modify";
    }

    //게시글 수정 저장 요청
    @PostMapping("/modify")
    public String modify1(int bulNum
            , DummyBoard dumBoard
    ) {
        log.info("bulNum: " + bulNum);
        boardService.modify(bulNum, dumBoard);
        return "redirect:/board/detail?bulNum=" + bulNum + "&vf=false";
    }

    //게시글 추천 요청
    @PostMapping("/recommend")
    public String recommend(int bulNum
            , @RequestParam("vf") boolean viewCntFlag
            , Model model
    ) {
        boardService.plusRecommend(bulNum);
        Board board = boardService.viewDetail(bulNum, viewCntFlag);
        log.info("추천수" + board.getRecommend());
        model.addAttribute("board", board);
        return "board/detail";
    }



}
