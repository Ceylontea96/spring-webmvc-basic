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

<style>
    .fileDrop {
        width: 800px;
        height: 400px;
        border: 1px dashed gray;
        display: flex;
        justify-content: center;
        align-items: center;
        font-style: 1.5em;
    }
    .uploaded-list {
        display: flex;
    }

    .uploaded-list a {
        display: flex;
        flex-direction: column;
        text-align: center;
        text-decoration: none;
        color: #000;
    }

    .uploaded-list a img {
        width: 100px;
        height: 100px;
        display: block;
    }

    .uploaded-list .thumbnail-box {
        display: flex;
    }
    
</style>
</head>
<body>

    <%@ include file="../include/header.jsp" %>

    <!-- 파일 업로드를 위한 form -->
    <form action="/upload" method="POST" enctype="multipart/form-data">
        <!-- multiple 속성을 주면 한 번에 여러개의 파일을 선택할 수 있게 됨 -->
        <input type="file" name="file" multiple>
        <button type="submit">업로드</button>
    </form>



    <!-- 비동기 통신을 통한 실시간 파일 업로드 -->
    <div class="fileDrop">
        <span>Drop Here!!</span>
    </div>

    <!-- 
        - 파일정보를 서버로 보내기 위해서는 <input type="file">이 필요함
        - 해당 태그는 사용자에게 나타내서 파일을 직접 탐색기를 통해 선택하게
          할 것인지 혹은 드래그앤 드롭으로만 처리할 것이냐에 따라 display
          상태를 결정
     -->
     <div class="uploadDiv">
         <input type="file" name="files" id="ajax-file" style="display: none;">
     </div>

     <!-- 업로드된 파일의 썸네일을 보여줄 영역 -->
     <div class="uploaded-list"></div>

    <%@ include file="../include/footer.jsp" %>

    <script>
        $(function() {//start jQuery
            
            //drag & drop 이벤트
            const $dropBox = $('.fileDrop');

            //드래그 진입 이벤트
            $dropBox.on('dragover dragenter', e => {
                e.preventDefault();
                $dropBox.css('border-color', 'red')
                        .css('background', 'lightgray');
            });

            //드래그 탈출 이벤트
            $dropBox.on('dragleave', e => {
                e.preventDefault();
                $dropBox.css('border-color', 'gray')
                        .css('background', 'transparent');
            });

            //드롭 이벤트
            $dropBox.on('drop', e => {
                e.preventDefault();//파일 내용이 바로 나오는걸 막아줌
                // alert('파일이 드롭됨!');

                //1. 드롭된 파일의 데이터를 읽기
                const fileDatas = e.originalEvent.dataTransfer.files;
                // console.log(fileDatas);

                //2. 읽은 파일 데이터를 input 태그에 저장
                const $fileInput = $('#ajax-file');
                $fileInput.prop('files', fileDatas);//prop()을 이용해서 files라는 이름으로 fileDatas를 저장

                //3. 담은 데이터를 서버로 전송
                // - 비동기로 파일을 보내려면 FormData 객체가 필요함!
                // 파일을 비동기로 전송하려면 header에 써야 할 메타데이터가 매우 많은데 그걸 대신 처리해주는게 formData
                const formData = new FormData();
                console.log('input : ', $fileInput);

                const sendFileList = $fileInput[0].files;
                //서버로 전송할 파일들을 formData에 담아 비동기 요청
                for (let file of sendFileList) {
                    formData.append('files', file);
                };

                //4. 업로드 비동기 요청
                const reqInfo = {
                    method: 'POST',
                    body: formData
                };

                fetch('/upload-ajax', reqInfo)
                    .then(res => res.json())
                    .then(pathList => {
                        console.log(pathList);
                        showFileData(pathList);
                    });
            });

            //드롭한 파일의 형식에 따라 태그를 만들어주는 함수
            function showFileData(pathList) {
                //경로예시: \2021\06\08\wiosaxlk;zjas_dog.gif
                for (let path of pathList) {
                    //이미지인지 아닌지에 따라 구분하여 처리
                    checkExtType(path);
                };
            };

            //확장자 판별 후 태그 생성 처리 함수
            function checkExtType(path) {
                //원본 파일명 추출
                let originFileName = path.substring(path.indexOf("_") + 1);

                //썸네일 박스
                const $div = document.createElement('div');
                    $div.classList.add('thumbnail-box');

                //삭제버튼
                const $delBtn = document.createElement('a');
                    $delBtn.classList.add('del-btn');
                    $delBtn.setAttribute('href', path);
                    $delBtn.textContent = 'X';

                //이미지인지 확장자 체크
                if (isImageFile(originFileName)) {
                    //이미지인 경우
                    //썸네일에 s_가 붙기 때문에 한 번 더 잘라줘야 함.
                    originFileName = originFileName.substring(originFileName.indexOf("_") + 1);

                    const $img = document.createElement('img');
                    $img.setAttribute('src', '/loadFile?fileName=' + path);
                    //대체 속성에는 원본 파일명
                    $img.setAttribute('alt', originFileName);

                    $div.appendChild($img);
                    $div.appendChild($delBtn);

                } else {
                    //이미지가 아닌 경우: 다운로드 링크 생성
                    const $link = document.createElement('a');
                    $link.setAttribute('href', '/loadFile?fileName=' + path);
                    $link.innerHTML = '<img src="/img/file_icon.jpg" alt="파일아이콘"> <span class="file-name">' + originFileName + '</span>';

                    $div.appendChild($link);
                    $div.appendChild($delBtn);

                }

                $('.uploaded-list').append($div);

            };

            //정규표현식으로 이미지파일 여부 확인하는 함수
            function isImageFile(originFileName) {
                const pattern = /jpg$|gif$|png$/i;
                return originFileName.match(pattern);
            }

            //파일 삭제 비동기 요청 클릭 이벤트
            $('.uploaded-list').on('click', '.del-btn', e => {

                //a태그의 기능을 막아놓지 않으면 GET으로 요청이 나감
                e.preventDefault();
                console.log(e.target.parentNode);
                
                const path = e.target.getAttribute('href')
                
                const $uploadedList = document.querySelector('.uploaded-list');

                fetch('/deleteFile?fileName=' + path, {method:'DELETE'})
                .then(res => res.text())
                .then(msg => {
                    if (msg === 'fileDeleteSuccess') {
                        const $thumbBox = e.target.parentNode;
                        $uploadedList.removeChild($thumbBox);
                    } else {
                        alert('파일 삭제 실패!');
                    }
                });
            });


        });//end jQuery
    </script>

</body>
</html>