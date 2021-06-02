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

        .pagination>li {
            justify-content: flex-end;
            margin-right: 5px;
        }

        .pagination>li>a {
            text-decoration: none;
            color: black;
        }

        .pagination>li>a:hover {
            color: yellowgreen;
        }

        .pagination>li.active {
            font-weight: bold;
            color: orangered;
            font-size: 1.1em;
        }

        .amount {
            width: 30%;
            display: flex;
            justify-content: flex-end;
            margin-bottom: 10px;
        }

        .amount a {
            display: block;
            color: #fff;
            background: #f00;
            width: 50px;
            height: 20px;
            border-radius: 5px;
            margin-right: 5px;
            text-align: center;
            font-weight: 700;
            text-decoration: none;
        }
    </style>

    <%@ include file="../include/static-head.jsp" %>
    <%@ include file="../include/header.jsp" %>
</head>

<body>

    <h1>게시글 목록</h1>
    <form action="/board/list" method="POST">

        <div class="amount">
            <a href="/board/list${pageMaker.makeParam(pageMaker.criteria.page, 10)}">10</a>
            <a href="/board/list${pageMaker.makeParam(pageMaker.criteria.page, 20)}">20</a>
            <a href="/board/list${pageMaker.makeParam(pageMaker.criteria.page, 30)}">30</a>
        </div>


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
                    <td><a
                            href="/board/detail${pageMaker.makeParam(pageMaker.criteria.page)}&bulNum=${board1.boardNo}&vf=true">${board1.title}</a>
                    </td>
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
                    <a href="/board/list${pageMaker.makeParam(pageMaker.beginPage-1)}">[prev]</a>
                </li>
            </c:if>

            <!-- li*5>a{[$]} -->
            <c:forEach var="i" begin="${pageMaker.beginPage}" end="${pageMaker.endPage}" step="1">
                <li data-page="${i}"><a href="/board/list${pageMaker.makeParam(i)}">[${i}]</a></li>
                <!-- makeParam은 메서드이기 때문에 ()를 붙여서 call 해줌 -->
            </c:forEach>

            <c:if test="${pageMaker.next}">
                <li>
                    <a href="/board/list${pageMaker.makeParam(pageMaker.endPage+1)}">[next]</a>
                </li>
            </c:if>
        </ul>

    </form>

    <!-- 검색창 영역 -->
    <div class="search">
        <form action="/board/list" id="search-form">
            <input type="hidden" name="amount" value="${cri.amount}">

            <select name="type">
                <option value="title" ${pageMaker.criteria.type=='title' ? 'selected' : '' }>제목</option>
                <option value="content" ${pageMaker.criteria.type=='content' ? 'selected' : '' }>내용</option>
                <option value="writer" ${pageMaker.criteria.type=='writer' ? 'selected' : '' }>작성자</option>
                <option value="titleContent" ${pageMaker.criteria.type=='titleContent' ? 'selected' : '' }>제목+내용
                </option>
            </select>

            <input type="text" name="keyword" placeholder="검색어를 입력!" value="${pageMaker.criteria.keyword}">

            <button type="submit">검색</button>

        </form>
    </div>
    <a href="/board/register">게시글 작성하기</a>


    <%@ include file="../include/footer.jsp" %>



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

        (function () {
            appendPageActive('${pageMaker.criteria.page}');
        }());
    </script>

</body>

</html>