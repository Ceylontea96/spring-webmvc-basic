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

    <h1>게시글 등록</h1>
    <form action="/board/register" method="POST">

        <p>
            # 작성자: <input type="text" name="writer">
        </p>

        <p>
            # 제목: <input type="text" name="title">
        </p>

        <p>
            # 내용: <input type="text" name="content">
        </p>
        <button type="submit">등록</button>
    </form>

    <a href="/board/list">글 목록보기</a>

</body>
</html>