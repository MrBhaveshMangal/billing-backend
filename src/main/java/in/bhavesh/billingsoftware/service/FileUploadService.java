package in.bhavesh.billingsoftware.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

//    for uploading the file and deleting the file
    String uploadFile(MultipartFile file);

    boolean deleteFile(String imgUrl);
}
