package com.spring.mvc.web.score.controller;

import com.spring.mvc.web.score.domain.Score;
import com.spring.mvc.web.score.repository.ScoreRepository;
import com.spring.mvc.web.score.service.ScoreService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Log4j2 //로그를 만들어주는 롬복 애너테이션
@RequestMapping("/score")//이 클래스들의 uri에는 /score가 자동으로 붙음
public class ScoreController {

    private final ScoreService scoreService; //실행중에는 바뀌지 않을테니 final

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }


    //점수 등록 화면 요청
    @GetMapping("/register")
    public String register() {
        log.info("/score/register GET 요청 발생! - ");
        return "score/write-form";
    }

    //점수 정보 저장 요청
    @PostMapping("/register")
    public String register(Score score) {
        log.info("/score/register POST - " + score);
        scoreService.register(score);

        return "redirect:/score/list"; //리다이렉트 'URL' ->
    }

    //점수 전체 조회 요청
    @GetMapping("/list") //조회는 GET
    public String list(Model model) {
        List<Score> scoreList = scoreService.getScoreList();
        log.info("/score/list GET 요청! - " + scoreList);
        model.addAttribute("scoreList", scoreList);
        model.addAttribute("count", scoreService.getCount());
        return "score/write-form";
    }

    //점수 정보 삭제 요청
    @GetMapping("/delete")
    public String delete(int stuNum) {
        log.info("/score/delete GET: " + stuNum);
        scoreService.delete(stuNum);
        return "redirect:/score/list";
    }

    //상세 점수 정보 요청
    @GetMapping("/detail")
    public String detail(int stuNum, Model model) {
        Score score = scoreService.viewDetail(stuNum);
        model.addAttribute("name", score.getName());
        model.addAttribute("kor", score.getKor());
        model.addAttribute("eng", score.getEng());
        model.addAttribute("math", score.getMath());
        model.addAttribute("total", score.getTotal());
        model.addAttribute("average", score.getAverage());
        return "score/detail";
    }
}
