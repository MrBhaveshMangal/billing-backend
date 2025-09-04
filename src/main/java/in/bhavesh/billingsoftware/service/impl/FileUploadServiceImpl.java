package in.bhavesh.billingsoftware.service.impl;

import in.bhavesh.billingsoftware.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${minio.bucket-name}")
    private String bucketName;

    private final MinioClient minioClient;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileExtension = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String key = UUID.randomUUID().toString() + "." + fileExtension;

        try (InputStream inputStream = file.getInputStream()) {

            // Upload file to MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(key)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // Return file URL (MinIO does not have public-read like AWS)
            return "http://localhost:9000/" + bucketName + "/" + key;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while uploading the image: " + e.getMessage()
            );
        }
    }

    @Override
    public boolean deleteFile(String imgUrl) {
        try {
            // filename extract kar liya (last / ke baad)
            String filename = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);

            // MinIO se object delete
            minioClient.removeObject(
                    io.minio.RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            );

            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while deleting the image: " + e.getMessage()
            );
        }
    }

}
