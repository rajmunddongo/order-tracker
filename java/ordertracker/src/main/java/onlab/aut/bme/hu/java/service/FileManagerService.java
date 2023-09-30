package onlab.aut.bme.hu.java.service;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asynchttpclient.uri.Uri;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;


@Log4j2
@Service
@AllArgsConstructor
public class FileManagerService {


    private RestTemplate restTemplate = new RestTemplate();
    @Value("${service.fileupload.url}")
    private String fileUploadServiceUrl;
    @Value("${service.filedownload.url}")
    private String fileDownloadServiceUrl;

    public void uploadFile(MultipartFile file, String name) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });
        body.add("name", name);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.exchange(fileUploadServiceUrl, HttpMethod.POST, requestEntity, String.class);
    }

    public byte[] downloadFile(String name) {
        String finalUrl = UriComponentsBuilder.fromHttpUrl(fileDownloadServiceUrl)
                .pathSegment(name)
                .build()
                .toUriString();

        HttpEntity<String> entity = new HttpEntity<>("");
        log.info("Sending request for downloading file {}", name);
        ResponseEntity<byte[]> response = restTemplate.exchange(
                finalUrl,
                HttpMethod.GET,
                entity,
                byte[].class
        );
        log.info("Response status code: {}", response.getStatusCode());
        log.info("Response body: {}", response.getBody());

        return response.getBody();
    }

}

