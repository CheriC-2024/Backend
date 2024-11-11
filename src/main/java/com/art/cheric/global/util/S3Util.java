package com.art.cheric.global.util;

import static software.amazon.awssdk.regions.Region.AP_NORTHEAST_2;

import java.net.URL;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
public class S3Util {
    private final S3Presigner presigner;
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;

    public S3Util(
            @Value("${cloud.aws.credentials.access-key}") String ACCESS_KEY,
            @Value("${cloud.aws.credentials.secret-key}") String SECRET_KEY) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        this.presigner = S3Presigner.builder()
                .region(AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();


        this.s3Client = S3Client.builder()
                .region(AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    // put presigned url 발급
    public URL generatePutPresignedUrl(String objectKey) {
        // S3에 업로드할 파일 요청 정보를 설정
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(objectKey)
                .build();

        // presigned URL의 만료 시간 설정 후, PUT presigned URL 생성 요청 설정
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))
                .build();

        // presigned PUT 요청 생성 및 URL 반환
        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
        return presignedRequest.url();
    }

    // S3 파일 삭제
    public void deleteFileFromS3(String objectKey) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(BUCKET)
                .key(objectKey)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

}
