package com.example.file_upload_service.controller;

import com.example.file_upload_service.domain.FileUploadObject;
import com.example.file_upload_service.domain.PostFileUploadResponse;
import com.example.file_upload_service.entity.FileEntity;
import com.example.file_upload_service.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileUploadController {

    @Autowired
    private FileUploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<PostFileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            uploadService.uploadFileEntity(file);
            message = "Upload successful for " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new PostFileUploadResponse(message));
        }  catch (Exception e) {
            message = "Upload failed for " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new PostFileUploadResponse(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileUploadObject>> getFiles() {
        List<FileUploadObject> files = uploadService.getAllFiles().map(file -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(file.getId())
                    .toUriString();
            return new FileUploadObject(
                    file.getName(),
                    fileDownloadUri,
                    file.getType(),
                    file.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileEntity fileEntity = uploadService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .body(fileEntity.getData());
    }
}
