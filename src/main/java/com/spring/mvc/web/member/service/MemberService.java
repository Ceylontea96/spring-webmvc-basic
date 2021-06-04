package com.spring.mvc.web.member.service;

import com.spring.mvc.web.member.domain.LoginInfo;
import com.spring.mvc.web.member.domain.Member;
import com.spring.mvc.web.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    //회원 가입 기능
    public void signUp(Member member) {
        String rp = member.getPassword();
        //DB에 저장하기 위해서 평문 비밀번호를 암호화
        member.setPassword(new BCryptPasswordEncoder().encode(rp));
        //새로 암호화한 비밀번호를 DB에 전달

        memberMapper.register(member);
    }

    //중복확인 기능
    /**
     *
     * @param type 중복검사 유형 (계정, 이메일)
     * @param keyword 중복검사값
     * @return 중복되었으면 true, 중복되지않았으면 false
     */
    public boolean isDuplicate(String type, String keyword) {
        Map<String, Object> checkDataMap = new HashMap<>();
        checkDataMap.put("type", type);
        checkDataMap.put("keyword", keyword);

        return memberMapper.isDuplicate(checkDataMap) == 1;
    }

    //회원 정보 조회 기능
    public Member getMember(String account) {
        return memberMapper.getUser(account);
    }


    //로그인 처리 기능
    public String login(LoginInfo inputMember) {
        Member dbMemeber = memberMapper.getUser(inputMember.getAccount());

        if (dbMemeber != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(inputMember.getPassword(), dbMemeber.getPassword())) {
                return "success";
            } else {
                return "pwFail";
            }
        } else {
            //dbMember가 null이면 해당 아이디의 회원이 없다는 것이기 때문에 잘못된 아이디임을 알려줌
            return "idFail";
        }

    }

    //자동로그인 처리를 위한 메서드
    /*
        1. 쿠키를 생성해서 현재 로그인한 유저의 브라우저 세션의 고유ID를 저장한 후
           로컬에 쿠키 전송
        2. 데이터베이스에 로그인한 유저의 자동로그인 관련컬럼에 데이터 추가
           (쿠키값, 로그인 유지 시간)
     */
    public void keepLogin(HttpSession session, HttpServletResponse response, String account) {
        //자동로그인 쿠키 생성 (세션 고유 아이디를 쿠키값으로 저장)
        String sid = session.getId();
        Cookie loginCookie = new Cookie("loginCookie", sid);
        //쿠키 설정값 세팅 (쿠키를 적용할 URL, 쿠키의 수명 등)
        loginCookie.setPath("/");//우리의 root 패키지부터 쿠키가 따라다님
        //90일
        int limitTime = 60 * 60 * 24 * 90;//초 * 분 * 시간 * 일
        loginCookie.setMaxAge(limitTime);//쿠키의 수명을 초 단위로 넣어줘야 함

        //로컬에 쿠키 전송
        //응답시에 쿠키 정보를 넣어서 전달해줘야하기 때문에 response를 사용해야 함.
        response.addCookie(loginCookie);
        //--------------------------------------------------------------

        //DB에 자동로그인 관련값 저장
        Map<String, Object> keepLoginMap = new HashMap<>();
        keepLoginMap.put("sid", sid);
        //DB에는 초 가 아니라 날짜 형식으로 저장해줘야 하기 때문에 limitTime을 쓸 수 없다.
        //System.currentTimeMillis는 현재시간을 밀리초로 나타낸 것
        //limitTime은 초 단위이기 때문에 1000을 곱해서 더해주면 밀리초 단위로 시간이 더해짐
        //int로 밀리세컨드 결과값을 담기에는 결과값이 너무 크기 때문에 long으로 형변환
        long expiredMs = System.currentTimeMillis() + ((long)limitTime * 1000);
        Date limitDate = new Date(expiredMs);//new Date에 밀리초를 넣으면 날짜 형식으로 바꿔줌
        keepLoginMap.put("lt", limitDate);
        keepLoginMap.put("acc", account);

        memberMapper.saveKeepLogin(keepLoginMap);
    }

    //자동로그인 해제 기능
    public void logout(String account) {
        Map<String, Object> logoutMap = new HashMap<>();
        logoutMap.put("sid", "none");
        logoutMap.put("lt", new Date());
        logoutMap.put("acc", account);
        memberMapper.saveKeepLogin(logoutMap);
    }


}
