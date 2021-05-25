package com.spring.mvc.web.api.v2;

import com.spring.mvc.web.score.domain.Board;
import com.spring.mvc.web.score.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// API를 만들 때 사용하는 컨트롤러 아노테이션
// @ResponseBody를 붙이지 않아도 됨(RestController에 포함되어있음)
@RestController
@RequestMapping("/api/v2")
@CrossOrigin // 다른 서버에서 요청이 올 때 CORS 정책을 해제한다.
public class ApiControllerV2 {
    //크롬에서 json viewer 확장앱 추가

    /*
        # ResponseEntity
        - 스프링 REST API가 응답을 할 때
        응답 상태 코드, 응답 헤더 등을 조작해서
        전송할 수 있게 도와주는 객체
     */
    @GetMapping("/hello")
    public ResponseEntity<String> hello(String p) {
        if (p.equals("hello")) {
            return new ResponseEntity<>("안녕안녕", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("페이지를 찾을 수 없음.", HttpStatus.NOT_FOUND);
        }
    }

    //String 배열을 보냈을 때
    @GetMapping("/hobby")
    public String[] hobby() {
        return new String[]{"음악감상", "축구", "꽃꽂이"};
    }

    //List로 보냈을 때
    @GetMapping("/major")
    public List<String> major() {
        return Arrays.asList("정보보안", "컴퓨터공학", "경역학", "수학과");
    }

    //객체를 보냈을 때
    @GetMapping("/board")
    public Board board() {
        return new Board("홍길동", "123", "456");
    }

    //Map으로 보냈을 때
    @GetMapping("/food")
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

//    @GetMapping("/boardlist")
//    public List<Board> list() {
//        return boardService.getBoardList();
//    }


}
