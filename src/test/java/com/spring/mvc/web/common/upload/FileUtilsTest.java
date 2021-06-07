package com.spring.mvc.web.common.upload;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileUtilsTest {

    @Test
    void uuidTest() {

        String fileName =  "dog.jpg";
        String newFileName = UUID.randomUUID().toString() + "_" +fileName;

        System.out.println("===============================");
        System.out.println("newFileName = " + newFileName);
        System.out.println("===============================");
    }

}