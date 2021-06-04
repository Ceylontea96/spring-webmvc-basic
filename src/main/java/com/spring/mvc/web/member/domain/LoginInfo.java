package com.spring.mvc.web.member.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//DTO 역할
@Getter @Setter
@ToString
public class LoginInfo {

    private String account;
    private String password;
    private boolean autoLogin;
}
