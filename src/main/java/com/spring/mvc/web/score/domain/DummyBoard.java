package com.spring.mvc.web.score.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class DummyBoard {

    private String newWriter; //수정된 작성자
    private String newTitle; //수정된 제목
    private String newContent; //수정된 내용

    public DummyBoard(String writer, String title, String content) {
        this.newWriter = writer;
        this.newTitle = title;
        this.newContent = content;
    }
}
