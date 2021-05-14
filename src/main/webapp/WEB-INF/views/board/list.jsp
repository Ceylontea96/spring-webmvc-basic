<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <style>

        table {
            width: 400px;
            border: 1px solid #000;
        }

        tr,
        td {
            border: 1px solid #000;
        }
    </style>
</head>

<body>

    <h1>게시글 목록</h1>
    <form action="/board/list" method="POST">

        <table>
            <tr>
                <td>번호</td>
                <td>작성자</td>
                <td>제목</td>
                <td>조회수</td>
                <td>추천수</td>
                <td>비고</td>
            </tr>
            <c:forEach var="board1" items="${boardList}">
                <tr>
                    <td>${board1.boardNo}</td>
                    <td>${board1.writer}</td>
                    <td><a href="/board/detail?bulNum=${board1.boardNo}&vf=true">${board1.title}</a></td>
                    <td>${board1.views}</td>
                    <td>${board1.recommend}</td>
                    <td><a href="/board/delete?bulNum=${board1.boardNo}">[삭제]</a></td>
                </tr>
            </c:forEach>
        </table>

    </form>
    <a href="/board/register">게시글 작성하기</a>



</body>

</html>