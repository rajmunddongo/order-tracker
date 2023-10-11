package com.example.file_upload_service.service;

import com.example.file_upload_service.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {

    @InjectMocks
    private FileUploadService service;
    @Mock
    FileRepository fileRepository;

    @Test
    void uploadTest() throws IOException {
        service.uploadFileEntity(Mockito.mock(MultipartFile.class), "name");
        Mockito.verify(fileRepository).save(any());
    }
}
