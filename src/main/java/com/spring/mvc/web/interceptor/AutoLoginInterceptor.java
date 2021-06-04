package com.spring.mvc.web.interceptor;

import com.spring.mvc.web.member.domain.Member;
import com.spring.mvc.web.member.repository.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Log4j2
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1. 자동 로그인 쿠키가 존재하는지 확인
        //쿠키를 읽어오는 작업이 우선임(로컬의 쿠키를 읽어오는 메서드)
        Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");

        //2. 자동로그인한 회원인 경우 쿠키값을 읽어서 DB와 대조
        if (loginCookie != null) {//로컬에 쿠키가 있다면 자동로그인을 했다는 것
            String value = loginCookie.getValue();//쿠키에 저장된 세션아이디 값
            //3. 쿠키 세션아이디를 통해 자동로그인한 회원의 정보 조회
            Member member = memberMapper.getUserBySessionId(value);
            if (member != null) {
                //4. 세션에 loginUser로 해당 회원정보 저장
                request.getSession().setAttribute("loginUser", member);
            }
        }
        //자동로그인을 했든 안했든 통과는 시켜줘야하기 때문에 전체적으로 return true
        return true;
    }

}
