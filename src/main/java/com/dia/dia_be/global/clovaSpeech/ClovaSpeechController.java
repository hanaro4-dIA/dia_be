package com.dia.dia_be.global.clovaSpeech;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
public class ClovaSpeechController {

    @Autowired
    final ClovaSpeechService clovaSpeechService;

    public ClovaSpeechController(ClovaSpeechService clovaSpeechService) {
        this.clovaSpeechService = clovaSpeechService;
    }

    @PostMapping("fileUpload")
    public String fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest req) throws IOException {
        String uploadPath = req.getServletContext().getRealPath("/upload");
        Path uploadPath_path = Paths.get(uploadPath);
        if (!Files.exists(uploadPath_path)) {
            Files.createDirectories(uploadPath_path); // 디렉토리가 없다면 생성
        }
        String fileName = uploadFile.getOriginalFilename();
        String filePath = uploadPath + "/" + fileName;

        try {
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            os.write(uploadFile.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        String resp = clovaSpeechService.stt(filePath);

        return resp;
    }
}
