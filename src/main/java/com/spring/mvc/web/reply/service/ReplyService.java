package com.spring.mvc.web.reply.service;

import com.spring.mvc.web.reply.domain.Reply;
import com.spring.mvc.web.reply.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyMapper replyMapper;

    //빠른 이해를 위해 SUCCESS를 1로 성공해놓고 사용.
    private static final int SUCCESS = 1;


    //댓글 등록 서비스
    public boolean register(Reply reply) {
        return replyMapper.save(reply) == SUCCESS;
        //결과가 1이면 true, 아니면 false 리턴
    }

    //댓글 삭제 서비스
    public boolean delete(int replyNo) {
        return replyMapper.delete(replyNo) == SUCCESS;
    }

    //댓글 수정 서비스
    public boolean modify(Reply reply) {
        return replyMapper.update(reply) == SUCCESS;
    }

    //단일 댓글 조회 서비스
    public Reply read(int replyNo) {
        return replyMapper.read(replyNo);
    }

    //특정 게시물 댓글 목록 조회 서비스
    public List<Reply> getList(int boardNo) {
        return replyMapper.getList(boardNo);
    }
}
