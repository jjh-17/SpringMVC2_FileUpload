package SpringMVC2.FileUpload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName; //업로드 파일 이름
    private String storeFileName; //저장할 파일 이름

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
