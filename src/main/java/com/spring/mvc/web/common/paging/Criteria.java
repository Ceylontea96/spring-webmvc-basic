package com.spring.mvc.web.common.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



//페이징, 검색 정보 객체
@Getter
@Setter
public class Criteria {

    private int page;       //페이지 넘버
    private int amount;     //한 페이지당 게시물 수

    private String type;    //검색 조건
    private String keyword; //검색어

    //기본 생성자에 설정된 값에 따라 값을 주지 않을때의 기본 설정값이 정해진다.
    public Criteria() {
//        this.page = 1;
//        this.amount = 10;
        this(1, 10);//아래의 생성자로 연결되므로 한줄로 처리 가능
    }

    public Criteria(int page, int amount) {
        this.page = page;
        this.amount = amount;
    }

    //setter를 직접 만드는 이유는 페이지를 마이너스로 입력하는 경우처럼
    //예외의 경우에 오류가 나기 때문에 1로 바꿔줌
    public void setPage(int page) {
        if (page <= 0) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    //amount에 음수 또는 너무 높은 수를 입력하면 10으로 설정
    public void setAmount(int amount) {
        if (amount <= 0 || amount > 100) {
            this.amount = 10;
            return;
        }
        this.amount = amount;
    }
}
