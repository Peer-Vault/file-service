package com.peer.vault.file_service.service;

import com.peer.vault.file_service.domain.FileMeta;
import com.peer.vault.file_service.dto.FileMetaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {

    FileMetaDTO saveFile(FileMetaDTO fileMetaDTO);

    byte[] loadFile(String hash);

    FileMeta getFileMetaByCID(String cid);

    List<Map<String, Object>> getFilesWithContentByUserId(Long userId);

    void deleteFileById(Long id);

    void shareFile(Long fileId, String email);
}
