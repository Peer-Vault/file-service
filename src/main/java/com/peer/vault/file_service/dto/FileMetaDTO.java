package com.peer.vault.file_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileMetaDTO {

    private String fileName;

    private String fileType;

    @JsonIgnore
    private MultipartFile file;

    private Long userId;

    private String description;
}
