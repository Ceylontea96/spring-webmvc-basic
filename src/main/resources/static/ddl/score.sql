CREATE SEQUENCE SEQ_SCORE;

CREATE TABLE score (
    stu_num NUMBER(10),
    stu_name VARCHAR2(20) NOT NULL,
    kor NUMBER(3) NOT NULL,
    eng NUMBER(3) NOT NULL,
    math NUMBER(3) NOT NULL,
    total NUMBER(3),
    avarage NUMBER(5, 2),
    CONSTRAINT pk_score PRIMARY KEY (stu_num)
);

SELECT * FROM score;


//게시판
CREATE SEQUENCE SEQ_BOARD;

CREATE TABLE board (
    board_no NUMBER(10),
    writer VARCHAR2(50) NOT NULL,
    title VARCHAR2(200) NOT NULL,
    content VARCHAR2(3000),
    recommend NUMBER(4),
    views NUMBER(5),
    post_time DATE NOT NULL,
    CONSTRAINT pk_board PRIMARY KEY (board_no)
);