package com.peer.vault.file_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileShareEvent {

    private String fileUrl;

    private String email;
}