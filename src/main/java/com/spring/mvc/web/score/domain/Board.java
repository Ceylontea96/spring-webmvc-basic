package com.spring.mvc.web.score.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    private Date regDate; //등록시간
//    private LocalDateTime postTime; //게시글 작성 시간

    //신규게시물 여부
    private boolean newArticle;

    //첨부파일 경로 리스트
    private List<String> filePathList;//jsp 파일 input태그의 name과 이름을 똑같이 만들어주면 알아서 받아옴

    public Board() {
        this.boardNo = ++sequence;
    }

    public Board(String writer, String title, String content) {
        this();
        this.writer = writer;
        this.title = title;
        this.content = content;
//        this.postTime = LocalDateTime.now();
    }

    public Board(ResultSet rs) throws SQLException {
        this.boardNo = rs.getInt("board_no");
        this.writer = rs.getString("writer");
        this.title = rs.getString("title");
        this.content = rs.getString("content");
        this.recommend = rs.getInt("recommend");
        this.views = rs.getInt("views");
//        this.postTime = (rs.getTimestamp("post_time")).toLocalDateTime();
    }

    public void plusViews() {
        this.views++;
    }


}
