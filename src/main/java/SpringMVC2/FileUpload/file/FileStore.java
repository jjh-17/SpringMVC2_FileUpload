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

    // 파일 최종 저장 경로 반환
    public String getFullPath(String file) {
        return fileDir + file;
    }

    // 여러 파일 저장 및 파일 정보들 반환
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        ArrayList<UploadFile> storeFileList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty())   storeFileList.add(storeFile(multipartFile));
        }

        return storeFileList;
    }

    // 파일 저장 및 파일 정보 반환
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        // 업로드 파일이 없으면 종료
        if (multipartFile.isEmpty())    return null;

        // 파일 저장
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        // 저장한 파일 이름과 서버 내 파일 이름 반환
        return new UploadFile(originalFilename, storeFileName);
    }

    // UUID를 이용하여 서버 내부에서 관리할 파일명을 생성(각기 유일)
    private String createStoreFileName(String file) {
        String type = getType(file);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + type;
    }

    // 업로드한 파일의 확장자를 분리하여 반환
    private String getType(String file) {
        int pos = file.lastIndexOf(".");
        return file.substring(pos + 1); //pos+1 이후 추출
    }
}
