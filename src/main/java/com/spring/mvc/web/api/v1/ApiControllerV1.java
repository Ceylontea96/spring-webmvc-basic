package com.spring.mvc.web.api.v1;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
public class ApiControllerV1 {
    //크롬에서 json viewer 확장앱 추가

    @GetMapping("/hello")
    @ResponseBody //클라이언트 요청을 비동기로 받음
    public String hello() {
        return "안녕안녕~~";
    }

    //String 배열을 보냈을 때
    @GetMapping("/hobby")
    @ResponseBody
    public String[] hobby() {
        return new String[] {"음악감상", "축구", "꽃꽂이"};
    }

    //List로 보냈을 때
    @GetMapping("/major")
    @ResponseBody
    public List<String> major() {
        return Arrays.asList("정보보안", "컴퓨터공학", "경역학", "수학과");
    }

    //객체를 보냈을 때
    @GetMapping("/board")
    @ResponseBody
    public Board board() {
        return new Board("홍길동", "123", "456");
    }

    //Map으로 보냈을 때
    @GetMapping("/food")
    @ResponseBody
    public Map<String, String> food() {
        Map<String, String> foodMap = new HashMap<>();
        foodMap.put("한식", "비빔밥");
        foodMap.put("양식", "파스타");
        foodMap.put("중식", "동파육");
        return foodMap;
    }

    //테스트용임 실제로 이렇게 쓰지 말 것
    @Autowired
    private BoardService boardService;

    @GetMapping("/boardlist")
    @ResponseBody
    public List<Board> list() {
        return boardService.getBoardList();
    }
}
