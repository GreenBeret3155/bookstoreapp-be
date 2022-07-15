package com.hust.datn.web.rest;

import com.hust.datn.service.FileService;
import com.hust.datn.service.dto.ResponseMessageDTO;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FileResource {

    @Autowired
    private FileService fileService;

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseMessageDTO> uploadFile(@Valid @RequestPart(value = "file", required = false) MultipartFile files) throws IOException {
        String fileType = FilenameUtils.getExtension(files.getOriginalFilename());
        String[] fileTypes = {"png", "jpg", "jpeg"};
        boolean contains = Arrays.stream(fileTypes).anyMatch(fileType::equals);
        if(contains == false){
            throw new BadRequestAlertException("Định dạng file không hợp lệ", fileType, "Định dạng file không hợp lệ");
        }
        if(files.getSize() > 500 * 1024 * 1024)
        {
            throw new BadRequestAlertException("Độ lớn vượt quá cho phéo >= 500 MB", fileType, "Độ lớn vượt quá cho phéo >= 500 MB");
        }
        String result = fileService.uploadFile(files.getBytes(),fileType);
        return ResponseEntity.ok()
            .body(new ResponseMessageDTO(200, result));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "f") String fileName) throws IOException {
        byte[] data = fileService.getFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
            .ok()
            .contentLength(data.length)
            .header("Content-type", "application/octet-stream")
            .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
            .body(resource);
    }
}
