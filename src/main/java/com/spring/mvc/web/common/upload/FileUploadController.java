package com.spring.mvc.web.common.upload;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@Log4j2
public class FileUploadController {

    //업로드파일 저장 경로(경로를 바꿀일이 있으면 바꾸기 쉽도록 상수로 저장)
    private static final String UPLOAD_PATH = "D:\\developing_mjh\\upload";

    //upload form.jsp를 열어주는 처리
    @GetMapping("/upload-form")
    public String uploadForm() {
        return "upload/upload-form";
    }

    //파일 업로드 요청 처리
    @PostMapping("/upload")
    //MultipartFile : 클라이언트가 전송할 파일의 여러 데이터를 담고있는 객체
    public String upload(@RequestParam("file") List<MultipartFile> fileList) throws IOException {

        for (MultipartFile file : fileList) {
            log.info("/upload POST - ");
            log.info("# 파일명 - " + file.getOriginalFilename());
            log.info("# 용량 - " + file.getSize());
            log.info("# 파일타입 - " + file.getContentType());
            log.info("=================================================");

            //업로드파일의 정보 객체를 생성
            //File클래스의 생성자를 통해 첫번재 파라미터로 저장경로를
            //두번째 파라미터로 파일명을 넣으세요
//            File uploadFile = new File(UPLOAD_PATH, file.getOriginalFilename());
            String responseFilePath = FileUtils.uploadFile(file, UPLOAD_PATH);
            log.info("저장 경로: " + responseFilePath);

        }

        return "upload/upload-form";
    }

    //비동기 파일 업로드 요청처리
    @PostMapping("/upload-ajax")
    @ResponseBody
    public ResponseEntity<List<String>> uploadAjax(List<MultipartFile> files) throws IOException {
//        jsp에서 formData에 files라는 이름으로 받아왔음
        log.info("/upload-ajax 비동기 POST 요청 - " + files.get(0).getOriginalFilename());

        //uploadFile 메서드가 리턴하는 파일 경로(String)을 리스트에 담아서 리턴
        List<String> pathList = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = FileUtils.uploadFile(file, UPLOAD_PATH);
            pathList.add(path);
        }
        ;

        return new ResponseEntity<>(pathList, HttpStatus.OK);
    }

    //파일 로딩 비동기 요청 처리
    //요청 URI : /loadFile?fileName=/2021/06/08/s_sdr32w45fkl_cat.jpg
    @GetMapping("/loadFile")
    @ResponseBody
    //파일은 byte배열에 담아서 보내줘야 함
    public ResponseEntity<byte[]> loadFile(String fileName) {
        log.info("/loadFile GET! - request file fullpath : " + (UPLOAD_PATH + fileName));

        //1. 클라이언트가 요청한 파일명을 이용하여
        //   파일의 전체 경로를 만들고 파일 정보 객체 생성
        //   보안상의 문제 때문에 UPLOAD_PATH 부분은 서버가 클라이언트에게 전달해야 함.
        File file = new File(UPLOAD_PATH + fileName);

        // /loadFile?FileName=파라미터를 이용해서 이상한 파일을 요청하는 경우
        // 파일을 찾아보고 없으면 404 상태코드와 함께 에러 리턴
        if (!file.exists()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        //2. 서버에 해당 파일이 저장되어 있다면 InputStream을 통해 파일을 로딩
        // 파일 관련 처리를 할 때는 필수적으로 예외처리를 해줘야 함
        // 스트림 사용 후 .close()로 닫아줘야 하는데 auto close를 사용하기 위해 try안에 넣어줌
        try (InputStream in = new FileInputStream(file)) {

            //3. 응답 헤더에 파일의 컨텐츠 미디어 타입을 설정
            //   ex) image/gif, text/html, application/json
            HttpHeaders headers = new HttpHeaders();

            //이미지 여부를 확인하기 위해 파일명에서 확장자 추출
            String ext = FileUtils.getFileExtension(fileName);

            MediaType mediaType = FileUtils.getMediaType(ext);

            //이미지 여부 확인에 따라 헤더 설정
            if (mediaType != null) {
                //이미지인 경우
                headers.setContentType(mediaType);
            } else {
                //이미지가 아닌 경우 : 다운로드 기능을 활성화하는 미디어타입 설정
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                //다운로드시 파일명을 원래대로 복구(UUID 없는 원래 파일명)
                //원래는 DB에 원본 파일명을 저장해놓고 DB에서 불러서 사용해야 함.
                fileName = fileName.substring(fileName.lastIndexOf("_") + 1);
                //파일명이 한글인 경우를 대비해 인코딩을 재설정
                String encoding = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

                //첨부파일 형식으로 다운로드하도록 헤더에 설정 추가
                //encoding된 파일명을 ""로 묶어줘야 하는데 인텔리제이에서 안넣어줘서 \"로 넣었음
                headers.add("Content-Disposition", "attachment; filename=\"" + encoding + "\"");
            }

            //4. 파일데이터를 바이트배열형식으로 클라이언트에 전송
            //org.apache.commons.io
            byte[] fileData = IOUtils.toByteArray(in);
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //서버에 저장된 파일을 삭제하는 비동기 요청처리
    //URI: /deleteFile?fileName=/2021/06/09/w3ejo234isd_abc.txt
    @DeleteMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName) throws IOException {

        log.info("/deleteFile DELETE! - " + fileName);
        //파일을 다룰때는 항상 예외처리하기
        try {
            //파일 삭제
            //이 경우 이미지파일은 썸네일만 삭제되고 원본이 남게됨
            File file = new File(UPLOAD_PATH + fileName);
            log.info("img thumbnail delete DELETE! - " + UPLOAD_PATH + fileName);
            file.delete();

            //따라서 이미지인 경우 원본까지 지우도록 해야한다.
            if (isImage(fileName)) {
                //원본을 지우는 코드
                //fileName => 썸네일 이미지 경로: /2021/06/09/s_sd1fj2o3si_abc.jpg
                //originalName => 원본 이미지 경로: /2021/06/09/sd1fj2o3si_abc.jpg
                int lastSlashIdx = fileName.lastIndexOf("/");
                String originalName
                        = fileName.substring(0, lastSlashIdx + 1)
                        + fileName.substring(lastSlashIdx + 3);
                File origin = new File(UPLOAD_PATH + originalName);
                log.info("img delete DELETE! - " + originalName);
                origin.delete();
            }
            return new ResponseEntity<>("fileDeleteSuccess", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //이미지 판별
    private boolean isImage(String fileName) {
        //1. getFileExtension으로 파일 확장자를 추출하고
        //2. 추출된 확장자가 이미지타입을 경우 getMediaType에 의해 값이 들어간다.
        //3. 따라서 해당 결과가 null일 경우 이미지파일이 아니고, null이 아닐경우 이미지 파일이다.
        return FileUtils.getMediaType(FileUtils.getFileExtension(fileName)) != null;
    }

}
