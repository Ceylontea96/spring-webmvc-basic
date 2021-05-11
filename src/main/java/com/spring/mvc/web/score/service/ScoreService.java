package com.spring.mvc.web.score.service;


import com.spring.mvc.web.score.domain.Score;
import com.spring.mvc.web.score.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //스프링이 서비스를 이용하기 위해서는 어노테이션을 넣어줘야한다.
public class ScoreService {

    private final ScoreRepository scoreRepository; //final 붙이고 alt + Enter로 매개변수 추가, 아래와 같은 생성자가 만들어지면 @Autowired 추가

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public void register(Score score) {
        score.calcTotalAvg();
        scoreRepository.save(score);
    }

    //점수 리스트를 받아오는 기능
    public List<Score> getScoreList() {
        return scoreRepository.findAll();
    }

    //점수 정보 삭제 기능
    public void delete(int stuNum) {
        scoreRepository.remove(stuNum);
    }

    //상세 정보 조회 기능
    public Score viewDetail(int stuNum) {
        return scoreRepository.findOne(stuNum);
    }
}
