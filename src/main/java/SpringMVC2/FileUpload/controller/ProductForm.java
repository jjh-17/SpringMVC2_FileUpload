package SpringMVC2.FileUpload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//상품 저장 폼
@Data
public class ProductForm {
    private Long productId;                 // 상품 ID
    private String productName;             // 상품 명
    private List<MultipartFile> imageFiles; // 이미지 파일들
    private MultipartFile attachFile;       // 첨부 파일
}
