package com.example.file_upload_service.repository;

import com.example.file_upload_service.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {

    public Optional<FileEntity> findByName(String name);
}
