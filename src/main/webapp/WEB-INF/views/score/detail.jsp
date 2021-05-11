<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>detail</title>
    <style>
        
    </style>
</head>

<body>

    <h1>${name}님 성적 정보</h1>

    <ul>
        <li>국어: ${kor}점</li>
        <li>영어: ${eng}점</li>
        <li>수학: ${math}점</li>
        <li>총점: ${total}점</li>
        <li>평균: ${average}점</li>
    </ul>

    <a href="/score/register">목록보기</a>
</body>

</html>