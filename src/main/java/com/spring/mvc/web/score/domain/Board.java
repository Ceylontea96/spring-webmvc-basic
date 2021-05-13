package com.spring.mvc.web.score.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@ToString
public class Board {

    private static int sequence;

    private int boardNo; //게시글 번호
    private String writer; //게시글 작성자
    private String title; //게시글 제목
    private String content; //게시글 내용
    private int views; //게시글 조회수
    private int recommend; //게시글 추천수
    private LocalDateTime postTime; //게시글 작성 시간

    public Board() {
        this.boardNo = ++sequence;
    }

    public Board(String writer, String title, String content) {
        this();
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.postTime = LocalDateTime.now();
    }

}
