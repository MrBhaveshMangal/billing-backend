package in.bhavesh.billingsoftware.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class AWSConfig {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.access-key}")
    private String accesskey;

    @Value("${minio.secret-key}")
    private String secretkey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)   // e.g. http://localhost:9000
                .credentials(accesskey, secretkey)
                .build();
    }

}
