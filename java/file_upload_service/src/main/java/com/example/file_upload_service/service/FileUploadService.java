package com.example.file_upload_service.service;

import com.example.file_upload_service.entity.FileEntity;
import com.example.file_upload_service.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileUploadService {

    @Autowired
    private FileRepository fileRepository;

    public FileEntity uploadFileEntity(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity entity = new FileEntity(fileName, file.getContentType(), file.getBytes());

        return fileRepository.save(entity);
    }

    public FileEntity getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<FileEntity> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}
