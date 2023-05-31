package SpringMVC2.FileUpload.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


//MultipartFile 인터페이스로 간소화
@Slf4j
@Controller
@RequestMapping("/spring")
public class SpringUploadController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam String productName,
                           @RequestParam MultipartFile file,
                           HttpServletRequest request) throws IOException {

        log.info("request={}", request);
        log.info("productName={}", productName);
        log.info("multipartFile={}", file);

        if (!file.isEmpty()) {
            //file.getOriginalFilename() : 업로드 파일 명
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 경로={}", fullPath);
            file.transferTo(new File(fullPath)); //파일 저장
        }

        return "upload-form";
    }
}
