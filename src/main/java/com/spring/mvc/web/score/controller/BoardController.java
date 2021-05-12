package com.spring.mvc.web.score.controller;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.domain.DummyBoard;
import com.spring.mvc.web.score.service.BoardService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String list(Model model) {
        List<Board> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    //게시글 작성화면 요청
    @GetMapping("/register")
    public String list() {
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
    public String detail(int bulNum, Model model) {
        Board board = boardService.viewDetail(bulNum);
        model.addAttribute("board", board);
        return "board/detail";
    }

    //게시글 수정 화면 요청
    @GetMapping("/modify")
    public String modify(int bulNum, Model model) {
        Board board = boardService.viewDetail(bulNum);
        model.addAttribute("board2", board);
        return "board/modify";
    }

    //게시글 수정 저장 요청
    @PostMapping("/modify")
    public String modify1(int bulNum, DummyBoard dumBoard) {
        log.info("bulNum: " + bulNum);
        boardService.modify(bulNum, dumBoard);
        return "redirect:/board/detail?bulNum=" + bulNum;
    }




}
