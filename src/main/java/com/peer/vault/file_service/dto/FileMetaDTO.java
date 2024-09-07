package com.peer.vault.file_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class FileMetaDTO {

    private Long id;

    private String fileName;

    private String fileType;

    @JsonIgnore
    private MultipartFile file;

    private String fileSize;

    private Long userId;

    private String CID;

    private String description;

    private LocalDateTime uploadDate;
}
