package com.dia.dia_be.global.clovaSpeech;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClovaSpeechService {

    @Value("${naver.clova.speech.credentials.invoke-url}")
    private String invokeUrl;
    @Value("${naver.clova.speech.credentials.secret-key}")
    private String secretKey;

    public String stt(String filePath) {
        StringBuffer response = null;
        System.out.println(invokeUrl);
        System.out.println(secretKey);
        try {
            String imgFile = filePath;
            File voiceFile = new File(imgFile);

            // URL 수정: multipart/form-data 방식으로 전달
            String boundary = "---Boundary";
            String apiURL = invokeUrl + "/recognizer/upload";
            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("X-CLOVASPEECH-API-KEY", secretKey);

            OutputStream outputStream = conn.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

            // media 파트 추가
            writer.append("--" + boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"media\"; filename=\"" + voiceFile.getName() + "\"").append("\r\n");
            writer.append("Content-Type: application/octet-stream\r\n\r\n").flush();

            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append("\r\n").flush();

            // params 파트 추가
            String params = "{\"language\":\"ko-KR\",\"completion\":\"sync\", \"callback\":\"\", \"fullText\":true, \"diarization\":{\"speakerCountMin\":2, \"speakerCountMax\":2}}";
            writer.append("--" + boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"params\"").append("\r\n\r\n");
            writer.append(params).append("\r\n").flush();

            // type 파트 추가
            writer.append("--" + boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"type\"").append("\r\n\r\n");
            writer.append("application/json").append("\r\n").flush();

            // 마지막 boundary
            writer.append("--" + boundary + "--").append("\r\n").flush();
            writer.close();

            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else { // 오류 발생
                log.error("stt failed!");
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }

            String inputLine;
            if (br != null) {
                response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                log.info("stt success : "+ response.toString());
            } else {
                log.error("can't get stt!");
            }
        } catch (Exception e) {
            log.error("can't get stt!");
        }

        return response != null ? response.toString() : null;
    }
}
