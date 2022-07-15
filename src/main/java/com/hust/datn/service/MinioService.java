package com.hust.datn.service;

import io.minio.*;
import io.minio.messages.Bucket;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MinioService {

    private final Logger log = LoggerFactory.getLogger(com.hust.datn.service.MinioService.class);

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.is.upload.file}")
    private boolean isUploadFile;

    public MinioService(){

    }

    public void createBucketIfNotExist(String bucketName, boolean objectLock) {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).objectLock(objectLock).build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFile(String filePath) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(filePath).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public byte[] getFile(String filePath) {
        try (InputStream stream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(bucket)
                .object(filePath)
                .build())) {
            if (stream == null) {
                try (InputStream streamAgain = minioClient.getObject(
                    GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(File.separator + "crm-orig" + File.separator + filePath)
                        .build())) {
                    return IOUtils.toByteArray(streamAgain);
                }
            }
            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String searchFile(String filePath){
        try {
            return minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build()
            ).etag();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String getRootFolder(String key) {
        return key;
    }
}

