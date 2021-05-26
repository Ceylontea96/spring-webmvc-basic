package com.spring.mvc.web.reply.repository;

import com.spring.mvc.web.reply.domain.Reply;
import com.spring.mvc.web.score.repository.BoardMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyMapperTest {

    @Autowired
    private ReplyMapper replyMapper;

    @Test
    @DisplayName("입력받은 Reply 데이터를 db에 정상적으로 저장해야 한다.")
    void insertTest() {
//        Reply reply = new Reply();
//        reply.setBoardNo(21);
//        reply.setReplyWriter("개똥이");
//        reply.setReplyText("해바라기");
//
//        replyMapper.save(reply);

        for (int i = 0; i < 20; i++) {
            Reply reply1 = new Reply();
            reply1.setBoardNo(9);
            reply1.setReplyText("테스트 댓글 " + i);
            reply1.setReplyWriter("테스트 댓글 작성자 " + i);

            replyMapper.save(reply1);

        }

    }

    @Test
    @DisplayName("특정 댓글을 DB에서 정상적으로 삭제해야 한다.")
    @Transactional @Rollback
    //transactional과 rollback을 해놓으면 테스트가 끝나면 변경사항이 원래대로 돌아옴
    void delteTest() {
        replyMapper.delete(3);
    }

    @Test
    @DisplayName("특정 댓글의 데이터를 변경하여 DB에 저장해야한다.")
    void modifyTest() {
        Reply reply = new Reply();
        reply.setReplyNo(21);
        reply.setReplyText("테스트 댓글 마지막");

        replyMapper.update(reply);
    }

    @Test
    @DisplayName("특정 댓글 1개를 조회할 수 있어야 한다.")
    void readTest() {
        Reply findReply = replyMapper.read(21);

        System.out.println("===========================================");
        System.out.println(findReply);

    }

    @Test
    @DisplayName("특정 게시물의 댓글 목록을 조회할 수 있어야 한다.")
    void getListTest() {

        List<Reply> replyList = replyMapper.getList(9);

        System.out.println("======================================");
        for (Reply r : replyList) {
            System.out.println(r);
        }

    }
}
