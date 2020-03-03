package com.limelight.server;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService {
    void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess);

    void deleteFileFromS3Bucket(String fileName);

    public UrlResource getResourceFromS3Bucket(String fileName);
}