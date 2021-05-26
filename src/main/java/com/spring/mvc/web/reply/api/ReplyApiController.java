package com.spring.mvc.web.reply.api;

import com.spring.mvc.web.reply.domain.Reply;
import com.spring.mvc.web.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reply")
@Log4j2
@CrossOrigin
@RequiredArgsConstructor//넣으면 아래코드 안넣어도 됨
//@Autowired
//public ReplyApiController(ReplyService replyService) {
//        this.replyService = replyService;
//        }
public class ReplyApiController {


    private final ReplyService replyService;


    /*
        REST 규칙
        /movie  GET     리스트 조회
        /movvie/pk GET  해당 movie 조회
        /
     */

    //댓글 목록 조회 요청 처리
    @GetMapping("/{bno}")
    //@Pathvariable("경로에서 무엇을 읽어올 것인지") 변수타입 변수명
    public ResponseEntity<List<Reply>> getList(@PathVariable("bno") int boardNo) {
        List<Reply> replies = replyService.getList(boardNo);
        log.info("apl/v1/reply"+boardNo+" GET!!");

        if (replies != null) {
            return new ResponseEntity<>(replies, HttpStatus.OK);
        } else {
            //리스트가 없으면 500 에러를 리턴
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //댓글 등록 처리 요청
    @PostMapping("")//url없이 post보내면 등록되도록
    public ResponseEntity<String> create(@RequestBody Reply reply) {//비동기로 받으면 json으로 오기 때문에 @RequestBody로 받아야 함
        log.info("/api/vi/reply POST - " + reply);

        //register의 리턴 타입이 boolean이므로 3항 연산자를 이용해서 성공시 insertSuccess를 리턴하고 실패시 500 에러를 리턴한다.
        return replyService.register(reply) ? new ResponseEntity<>("insertSuccess", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //댓글 수정 요청 처리
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@PathVariable("rno") int replyNo, @RequestBody Reply reply) {
        reply.setReplyNo(replyNo);

        log.info("/api/v1/reply/" + replyNo + " PUT - " + reply);

        return replyService.modify(reply)
                ? new ResponseEntity<>("modSuccess", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //댓글 삭제 요청 처리
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") int replyNo) {
        log.info("/api/v1/reply/" + replyNo + " DELETE!!");

        return replyService.delete(replyNo)
                ? new ResponseEntity<>("delSuccess", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
