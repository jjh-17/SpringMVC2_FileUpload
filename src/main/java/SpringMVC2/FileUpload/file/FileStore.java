package SpringMVC2.FileUpload.file;

import SpringMVC2.FileUpload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String file) {
        return fileDir + file;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        ArrayList<UploadFile> storeFileList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileList.add(storeFile(multipartFile));
            }
        }

        return storeFileList;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        //업로드 파일이 없음
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName);
    }


    //UUID를 이용하여 서버 내부에서 관리할 파일명을 생성(각기 유일)
    public String createStoreFileName(String file) {
        String type = getType(file);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + type;
    }

    //업로드한 파일의 확장자를 분리하여 반환
    public String getType(String file) {
        int pos = file.lastIndexOf(".");
        return file.substring(pos + 1); //pos+1 이후 추출
    }
}
