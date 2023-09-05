package com.example.file_upload_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadObject {

    private String name;
    private String url;
    private String type;
    private long size;
}
