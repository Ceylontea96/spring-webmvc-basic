<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>

    <link rel="stylesheet" href="/css/main.css">
    <style>

        table {
            width: 400px;
            border: 1px solid #000;
        }

        tr,
        td {
            border: 1px solid #000;
        }

        .pagination {
            width: 100%;
            margin-top: 10px;
            list-style: none;
            display: flex;
        }
        .pagination > li {
            justify-content: flex-end;
            margin-right: 5px;
        }

        .pagination > li > a {
            text-decoration: none;
            color: black;
        }

        .pagination > li > a:hover {
            color: yellowgreen;
        }

        .pagination > li.active {
            font-weight: bold;
            color: orangered;
            font-size: 1.1em;
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

        <!-- 페이지 영역 -->
<ul class="pagination">
   
    <c:if test="${pageMaker.prev}">
       <li>
          <a href="/board/list?page=${pageMaker.beginPage - 1}">[prev]</a>
       </li>
    </c:if>
 
    <!-- li*5>a{[$]} -->
    <c:forEach var="i" begin="${pageMaker.beginPage}" end="${pageMaker.endPage}" step="1">
       <li><a href="/board/list?page=${i}">[${i}]</a></li>
    </c:forEach>
 
    <c:if test="${pageMaker.next}">
       <li>
          <a href="/board/list?page=${pageMaker.endPage + 1}">[next]</a>
       </li>
    </c:if>
 </ul>

    </form>
    <a href="/board/register">게시글 작성하기</a>



    <script>

        //현재 위치한 페이지넘버에 클래스 active를 부여하는 함수 정의
        function appendPageActive(curPageNum) {
            const $ul = document.querySelector('.pagination');
            for (let $li of [...$ul.children]) {
                if ($li.dataset.page === curPageNum) {
                    $li.classList.add('active');
                }
            }
        };

        (function() {
            appendPageActive('${pageMaker.criteria.page}');
        }());

    </script>

</body>

</html>