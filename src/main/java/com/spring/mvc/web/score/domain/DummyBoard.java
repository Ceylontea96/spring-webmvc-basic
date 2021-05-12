package com.spring.mvc.web.score.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class DummyBoard {

    private String newWriter;
    private String newTitle;
    private String newContent;

    public DummyBoard(String writer, String title, String content) {
        this.newWriter = writer;
        this.newTitle = title;
        this.newContent = content;
    }
}
