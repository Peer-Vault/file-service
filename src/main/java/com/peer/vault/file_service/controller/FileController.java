package com.peer.vault.file_service.controller;

import com.peer.vault.file_service.domain.FileMeta;
import com.peer.vault.file_service.dto.FileMetaDTO;
import com.peer.vault.file_service.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileMetaDTO> uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("fileName") String fileName,
                             @RequestParam("fileType") String fileType,
                             @RequestParam("userId") Long userId,
                             @RequestParam("description") String description) {
        FileMetaDTO fileMetaDTO = new FileMetaDTO();
        fileMetaDTO.setFile(file);
        fileMetaDTO.setFileName(fileName);
        fileMetaDTO.setFileType(fileType);
        fileMetaDTO.setUserId(userId);
        fileMetaDTO.setDescription(description);

        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.saveFile(fileMetaDTO));
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<byte[]> getFile(@PathVariable("hash") String hash) {
        FileMeta fileMeta = fileService.getFileMetaByCID(hash);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileMeta.getFileName() + "." + fileMeta.getFileType());

        byte[] bytes = fileService.loadFile(hash);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
    }

    @PostMapping("/share")
    public ResponseEntity<Void> shareFile(
            @RequestParam("fileId") Long fileId,
            @RequestParam("email") String email) {
        fileService.shareFile(fileId, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFileById(@RequestParam("id") Long id) {
        fileService.deleteFileById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/files")
    public ResponseEntity<List<Map<String, Object>>> getFilesByUserId(@PathVariable("userId") Long userId) {
        List<Map<String, Object>> filesWithContent = fileService.getFilesWithContentByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(filesWithContent);
    }
}

