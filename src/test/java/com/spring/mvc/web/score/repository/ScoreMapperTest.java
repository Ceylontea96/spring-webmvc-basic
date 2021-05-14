package com.spring.mvc.web.score.repository;

import com.spring.mvc.web.score.domain.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreMapperTest {


    @Autowired
    private ScoreMapper mapper;

    @Test
    void saveTest() {

        Score score = new Score();
        score.setName("고길동");
        score.setKor(97);
        score.setEng(96);
        score.setMath(99);
        score.calcTotalAvg();

        mapper.save(score);
    }

    @Test
    void removeTest() {
        mapper.remove(22);
    }

    @Test
    void findAllTest() {
        List<Score> list = mapper.findAll();

        for (Score score : list) {
            System.out.println(score);
        }

        assertEquals(list.size(), 2);
    }

    @Test
    void findOneTest() {
        System.out.println(mapper.findOne(24));

        assertEquals("고길동", mapper.findOne(24).getName());
    }
}