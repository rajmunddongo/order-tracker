package onlab.aut.bme.hu.java.controller;

import onlab.aut.bme.hu.java.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    FileManagerService fileUploadService;

    @PostMapping("/indexphoto/upload")
    public ResponseEntity<Boolean> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam("name") String name) throws IOException {
        fileUploadService.uploadFile(file, name);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/indexphoto/download/{name}")
    public ResponseEntity<String> downloadFile(@PathVariable("name") String name) {
        return ResponseEntity.ok( Base64.getEncoder().encodeToString(fileUploadService.downloadFile(name)));
    }
}
