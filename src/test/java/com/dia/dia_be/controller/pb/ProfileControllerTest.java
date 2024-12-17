package com.dia.dia_be.controller.pb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testUpdateProfile() throws Exception {
        // API URL
        String url = "/pb/profile";

        // Introduce parameter
        String introduce = "This is my introduction.";

        // Mock Multipart File
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                       // RequestParam name
                "profile-image.jpg",          // Original file name
                "image/jpeg",                 // MIME type
                "<binary data>".getBytes()    // File content
        );

        // HashTagList 생성
        List<String> hashtags = List.of("tag1","tag2","tag3");

        // HashTagList를 JSON 형식으로 변환
        String hashTagListJson = objectMapper.writeValueAsString(hashtags);

        // MockMvc PUT 멀티파트 요청
        mockMvc.perform(multipart(url)
                        .file(mockFile)                          // File 첨부
                        .param("introduce", introduce)           // Introduce 필드
                        .param("hashtags", hashTagListJson)   // HashTagList 필드
                        .contentType(MediaType.MULTIPART_FORM_DATA) // Content-Type 설정
                        .with(request -> {                       // PUT 메서드로 전환
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())              // HTTP 200 OK 기대
                .andExpect(jsonPath("$.introduce").value("This is my introduction."))
                .andDo(print());                        // 요청/응답 로그 출력
    }

    @Test
    public void testUpdateProfileWithNoPng() throws Exception {
        // API URL
        String url = "/pb/profile";

        // Introduce parameter
        String introduce = "This is my introduction.";


        // HashTagList 생성
        List<String> hashtags = List.of("tag1","tag2","tag3");

        // HashTagList를 JSON 형식으로 변환
        String hashTagListJson = objectMapper.writeValueAsString(hashtags);
        System.out.println(hashTagListJson);
        // MockMvc PUT 멀티파트 요청
        mockMvc.perform(multipart(url)
                        .param("introduce", introduce)           // Introduce 필드
                        .param("hashtags", hashTagListJson)   // HashTagList 필드
                        .contentType(MediaType.MULTIPART_FORM_DATA) // Content-Type 설정
                        .with(request -> {                       // PUT 메서드로 전환
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())              // HTTP 200 OK 기대
                .andExpect(jsonPath("$.introduce").value("This is my introduction."))
                .andDo(print());                         // 요청/응답 로그 출력
    }
}
