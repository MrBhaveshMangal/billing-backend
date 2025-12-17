package in.bhavesh.billingsoftware.service.impl;

import in.bhavesh.billingsoftware.service.FileUploadService;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("prod")
@Primary
public class DummyFileUploadServiceImpl implements FileUploadService {

    @Override
    public String uploadFile(MultipartFile file) {
        return null; // prod mein upload disabled
    }

    @Override
    public boolean deleteFile(String imgUrl) {
        return true;
    }
}
