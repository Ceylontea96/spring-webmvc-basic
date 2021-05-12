<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
</head>
<body>

    <h1> ${board.boardNo}번 게시글 내용</h1>

    <p># 글번호 : ${board.boardNo}</p>
    <p># 작성자 : ${board.writer}</p>
    <p># 제목 : ${board.title}</p>
    <p># 내용 : <br> ${board.content}</p>

    <a href="/board/list">글 목록보기</a>
    <a href="/board/modify?bulNum=${board.boardNo}">글 수정하기</a>
</body>
</html>