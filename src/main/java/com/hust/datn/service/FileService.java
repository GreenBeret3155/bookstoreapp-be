package com.hust.datn.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;
import java.time.Instant;
import java.util.Optional;

@Service
public class FileService {
    private static final String ROOT_FOLDER = "hung/";

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public String uploadFile(byte[] file, String fileType) {
        UUID uuid = UUID.randomUUID();
        final String fileName = uuid + "." + fileType;
        try {
            minioClient.putObject(
                PutObjectArgs.builder().bucket(bucket).object(ROOT_FOLDER + fileName).stream(new ByteArrayInputStream(file), file.length, -1).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    public byte[] getFile(String fileName) {
        try (InputStream stream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(bucket)
                .object(ROOT_FOLDER + fileName)
                .build())) {
            if (stream == null) {
                try (InputStream streamAgain = minioClient.getObject(
                    GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(File.separator + "crm-orig" + File.separator + ROOT_FOLDER + fileName)
                        .build())) {
                    return IOUtils.toByteArray(streamAgain);
                }
            }
            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
