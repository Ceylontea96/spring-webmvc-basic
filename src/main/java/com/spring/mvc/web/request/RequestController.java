package com.spring.mvc.web.request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

//스프링 컨테이너에 해당 클래스의 객체가 빈(컨트롤러기능)으로 등록됨
@Controller
public class RequestController {

    //요청 처리 메서드 (서블릿 역할)
    //해당 URL에 따른 GET요청을 처리하게 함.
//    @RequestMapping(value = "/req/test", method = RequestMethod.POST)
    @GetMapping("/req/test")
    public String test() {
        System.out.println("## /req/test GET요청 발생!!");
//        return "/WEB-INF/views/test.jsp"; //리턴은 뷰 파일 포워딩 개념
        return "test";
    }

    // 요청 URI : /request/req-ex
    @GetMapping("/request/req-ex")
    public String reqEx() {
        System.out.println("## /request/req-ex GET요청 발생!!");

        return "request/req-ex";
    }

    @GetMapping("/request/basic")
    public String reqGet() {
        System.out.println("## /request/basic GET요청 발생!!");

        return "request/req-ex";
    }

    @PostMapping("/request/basic")
    public String reqPost() {
        System.out.println("## /request/basic POST요청 발생!!");

        return "request/req-ex";
    }

    //요청 파라미터 받기 (/req/basic ? xxx=yyy&zzz=xxx)
    //단점 : 파라미터를 받아올 때 String으로 받아오기 때문에 정수형같은 경우 형변환을 해줘야함
    @GetMapping("/request/param")
    public String param(HttpServletRequest request) {
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        System.out.println("money = " + money);
        System.out.println("name = " + name);

        return "test";
    }

    //요청 파라미터 받기2 - @RequestParam 이용하기
    //단점 : 파라미터가 많아질 시 관리가 힘듦
    @GetMapping("/request/param2")
    public String param2(@RequestParam("money") int apple //실무팁1 - 다음 파라미터는 콤마와 함께 내려줌(주석처리등을 쉽게 하기 위해)
            , String name
            , String addr
    ) { //실무팁2 - 괄호도 아래로 내려줌
        System.out.println("money*2 = " + apple * 2);
        System.out.println("name = " + name);

        return "test";
    }

    //요청 파라미터 받기3 - 커맨드 객체 사용
    //클래스 객체를 만들어서 관리
    //파라미터가 적을때는 비효율적
    @GetMapping("/request/param3")
    public String param3(User user) {
        System.out.println("user = " + user);
        return "test";
    }

    @GetMapping("/request/join-form")
    //요청 URL과 리턴 경로가 같다면 void처리하고 리턴을 생략가능
    public void form(User user) {

    }

    //퀴즈
    @GetMapping("/request/quiz")
    public String quiz() {

        return "request/req-quiz";
    }

    @PostMapping("/request/quiz")
    public String quiz(@RequestParam String userId
            , String userPw
    ) {
        return userId.equals("abc1234") && userPw.equals("xxx4321")
                ? "request/success" : "request/fail";
    }



}
