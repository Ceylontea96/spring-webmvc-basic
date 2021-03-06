<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>

    <%@ include file="../include/static-head.jsp" %>
    <%@ include file="../include/header.jsp" %>
</head>

<body>

    <h1> ${board2.boardNo}번 게시글 수정</h1>

    <form action="/board/modify" method="POST">

        <input type="hidden" name="bulNum" value="${board2.boardNo}">
        <input type="hidden" name="vf" value="false">
        <p># 글번호 : ${board2.boardNo}</p>
        <p># 작성자 : <input type="text" value="${board2.writer}" name="newWriter"></p>
        <p># 제목 : <input type="text" value="${board2.title}" name="newTitle"></p>
        <p># 내용 : <br> <input type="text" value="${board2.content}" name="newContent"></p>

        <div>
            <button type="submit">저장</button>
        </div>
        
    </form>
    <a href="/board/list">글 목록보기</a>



</body>

</html>