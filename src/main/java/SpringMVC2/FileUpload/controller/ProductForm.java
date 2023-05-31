package SpringMVC2.FileUpload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//상품 저장 폼
@Data
public class ProductForm {

    private Long productId;
    private String productName;
    private List<MultipartFile> imageFiles; //이미지 다중 업로드
    private MultipartFile attachFile;
}
