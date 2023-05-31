package SpringMVC2.FileUpload.domain;

import lombok.Data;

import java.util.List;

@Data
public class Product {

    private Long id;
    private String productName;
    private UploadFile attachFile; //첨부 파일
    private List<UploadFile> imageFiles; //이미지 파일 리스트
}
