package com.spring.mvc.web.request;

import lombok.*;

import java.util.List;

//Setter와 Getter가 있어야 함
@Setter
@Getter
@ToString
//@NoArgsConstructor -> final 설정
//@AllArgsConstructor -> 파라미터 초기화에 사용
public class User {

    //파라미터값을 변수고 다 설정해야 함(대 소문자 맞춰서)
    private String userId;
    private String userPw;
    private String userName;
    private List<String> hobby;

    //기본 생성자 필수!
    public User() {
        System.out.println("User 커맨드 객체 생성됨!");
    }

    public void setHobby(List<String> hobby) {
        System.out.println("hobby Setter 호출됨!");
        this.hobby = hobby;
    }


}
