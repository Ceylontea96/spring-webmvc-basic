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
</head>
<body>

    <%@ include file="../include/header.jsp" %>

    <!-- 파일 업로드를 위한 form -->
    <form action="/upload" method="POST" enctype="multipart/form-data">
        <!-- multiple 속성을 주면 한 번에 여러개의 파일을 선택할 수 있게 됨 -->
        <input type="file" name="file" multiple>
        <button type="submit">업로드</button>
    </form>

    <%@ include file="../include/footer.jsp" %>

</body>
</html>