<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

    <%
    int age = Integer.parseInt(request.getParameter("age")); //스트링을 정수로 바꾸기위한 작업.

    String txt = "";
    if (age > 19) {
        txt = "성인";
    } else {
        txt = "미성년자";
    } 
    %>

    <h1>check-age.jsp 파일입니다.</h1>

    <h2>당신은 <%= txt %>입니다.</h2>

</body>
</html>