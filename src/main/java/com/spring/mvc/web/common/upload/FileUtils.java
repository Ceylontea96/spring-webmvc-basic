package com.spring.mvc.web.common.upload;

import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//파일 업로드 관련 공통 처리를 위한 유틸 클래스
public class FileUtils {

    //이미지 구분을 위한 확장자 맵 만들기
    private static final Map<String, MediaType> mediaMap;

    //스태틱을 초기화 하기 위해서는 스태틱 이니셜라이저 사용
    static {
        mediaMap = new HashMap<>();
        mediaMap.put("JPG", MediaType.IMAGE_JPEG);
        mediaMap.put("GIF", MediaType.IMAGE_GIF);
        mediaMap.put("PNG", MediaType.IMAGE_PNG);

    }

    //확장자를 전달하면 해당 미디어타입을 리턴하는 메서드
    public static MediaType getMediaType(String ext) {
        ext = ext.toUpperCase();
        if (mediaMap.containsKey(ext)) {
            return mediaMap.get(ext);
        } else {
            return null;
        }
    }

    //1. 사용자가 파일을 업로드했을 때 저장 처리 및 파일명을 리턴하는 메서드
    public static String uploadFile(MultipartFile file, String uploadPath) throws IOException {

        //중복없는 파일명으로 변환
        //dog.jpg => 1yiu1234j3io9i12op13pl123_dog.jpg
        String newFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        //저장 경로에 날짜별 폴더를 생성
        //ex) D:/developing_mjh/upload/2021/06/07
        String newUploadPath = getNewUploadPath(uploadPath);

        //업로드 수행
        File uploadFile = new File(newUploadPath, newFileName);
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
            만약 업로드한 파일이 이미지 파일이라면? 추가로 썸네임을 생성해서 저장하고
            해당 썸네일의 경로를 클라이언트에 응답 (저장은 원본, 보여주는건 썸네일)
            이미지가 아니라면?? 그냥 다운로드할 수 있게 일반 파일경로를 리턴
         */

        //클라이언트가 서버에 있는 파일을 로딩하려면 해당 파일이
        //어디에 저장되어있는지 정보가 필요하다. 따라서
        //중복되는 경로를 제외한(D:/*/upload) 경로를 리턴해야 함. (보안상의 이유로 파일 저장 경로를 숨기기 위함도 있음)
        //  응답할 파일명: /2021/**/파일명
        //ex) D:/developing_mjh/upload/2021/06/07
        //앞의 불필요한 경로의 길이를 구해서 그 글자수만큼을 제외하고 나머지를 구함
        String responseFileName = newUploadPath.substring(uploadPath.length()) + File.separator + newFileName;
        //전달되는 경로명 예시 : /2021/06/07/파일명

        //이미지인지 확인하기 위해 확장자 추출
        String ext = getFileExtension(newFileName);

        if (getMediaType(ext) != null) {
            //이미지인 경우
            String thumbnailPath = makeThumbnail(newUploadPath, newFileName);
            return thumbnailPath.substring(uploadPath.length()).replace("\\", "/");//기존 경로 제외
        } else {
            //이미지가 아닌 경우
        return responseFileName.replace("\\", "/");
        }
    }

    //썸네일 이미지를 생성하여 저장하고 해당 저장경로를 반환하는 메서드
    private static String makeThumbnail(String newUploadPath, String newFileName) throws IOException {
        //원본 이미지 읽어오기
        BufferedImage srcImg = ImageIO.read(new File(newUploadPath, newFileName));

        //# 썸네일 생성 작업
        //1. 원본이미지 리사이징
        //원본 이미지, 자동 사이징, 세로는 딱 맞게, 100픽셀로
        BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);

        //2. 썸네일 이미지를 저장할 경로 생성
        //원본 파일명 앞에 s_가 붙으면 썸네일
        String thumbnailPath = newUploadPath + File.separator + "s_" + newFileName;

        //3. 썸네일 이미지파일 객체 생성
        File newFile = new File(thumbnailPath);

        //4. 썸네일 이미지 서버에 저장
        ImageIO.write(destImg, getFileExtension(newFileName), newFile);

        //5. 썸네일 저장 경로 리턴
        return thumbnailPath;
    }

    //파일명에서 확장자를 추출해주는 메서드
    //컨트롤러에서 사용하기 위해 public으로 풀어줌
    public static String getFileExtension(String newFileName) {
        return newFileName.substring(newFileName.lastIndexOf(".") + 1);
    }

    //날짜별 폴더 생성 메서드
    private static String getNewUploadPath(String uploadPath) {
        LocalDate now = LocalDate.now();
        int y = now.getYear();
        int m = now.getMonthValue();
        int d = now.getDayOfMonth();

        //폴더 생성
        String[] dateInfo = {String.valueOf(y), len2(m), len2(d)};
        for (String date : dateInfo) {
            //File.separator는 운영체제에 맞게 경로구분 문자열을 만들어줌.
            uploadPath += File.separator + date;
            //이 경로대로 폴더 생성
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
        }
        return uploadPath;
    }

    //한 자리수 월, 일을 항상 2자리로 만들어주는 메서드
    private static String len2(int n) {
        return n < 10 ? "0" + n : "" + n;
    }
}
