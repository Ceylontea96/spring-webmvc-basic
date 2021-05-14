package com.spring.mvc.web.score.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class DummyBoard {

    private int bulNum;
    private String newWriter; //수정된 작성자
    private String newTitle; //수정된 제목
    private String newContent; //수정된 내용

//    public DummyBoard() {
//    }

    public DummyBoard(String newWriter, String newTitle, String newContent) {
        this.newWriter = newWriter;
        this.newTitle = newTitle;
        this.newContent = newContent;
    }
}
