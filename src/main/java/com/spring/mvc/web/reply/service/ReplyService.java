package com.spring.mvc.web.reply.service;

import com.spring.mvc.web.common.paging.Criteria;
import com.spring.mvc.web.common.paging.PageMaker;
import com.spring.mvc.web.reply.domain.Reply;
import com.spring.mvc.web.reply.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //리턴 결과물을 두가지로 만듦. (댓글배열과 총 게시물 수)
    public Map<String, Object> getList(int boardNo, Criteria criteria) {
        Map<String, Object> replyMap = new HashMap<>();
        replyMap.put("replyList", replyMapper.getList(boardNo, criteria));
        int count = replyMapper.getCount(boardNo);
        replyMap.put("count", count);
        replyMap.put("pageInfo", new PageMaker(criteria, count));//board controller에서 page정보를 전달할 때 비동기로 한게 아니라서 json으로 전달
        return replyMap;
    }
}
