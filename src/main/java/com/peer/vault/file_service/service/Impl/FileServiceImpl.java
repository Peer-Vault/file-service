package com.peer.vault.file_service.service.Impl;


import com.peer.vault.file_service.client.UserClient;
import com.peer.vault.file_service.config.IPFSConfig;
import com.peer.vault.file_service.domain.FileMeta;
import com.peer.vault.file_service.domain.UserCredential;
import com.peer.vault.file_service.dto.EmailRequest;
import com.peer.vault.file_service.dto.FileMetaDTO;
import com.peer.vault.file_service.repository.FileMetaRepository;
import com.peer.vault.file_service.service.FileService;
import com.peer.vault.file_service.service.email.EmailService;
import com.peer.vault.file_service.util.FileSizeUtil;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    IPFSConfig ipfsConfig;

    @Autowired
    private FileMetaRepository fileMetaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserClient userClient;




    @Override
    public FileMetaDTO saveFile(FileMetaDTO fileMetaDTO) {
        try {
            IPFS ipfs = ipfsConfig.ipfs;

            InputStream inputStream = new ByteArrayInputStream(fileMetaDTO.getFile().getBytes());
            NamedStreamable.InputStreamWrapper is = new NamedStreamable.InputStreamWrapper(inputStream);
            MerkleNode response = ipfs.add(is).get(0);
            String cid = response.hash.toBase58();
            String fileSize = FileSizeUtil.convertToReadableSize(fileMetaDTO.getFile().getSize());
            String fileUrl = "http://localhost:3000/file/download/" + cid;

            FileMeta fileMeta = new FileMeta();
            fileMeta.setFileName(fileMetaDTO.getFileName());
            fileMeta.setFileType(fileMetaDTO.getFileType());
            fileMeta.setFileSize(fileSize);
            fileMeta.setFileUrl(fileUrl);
            fileMeta.setCID(cid);
            fileMeta.setUploadDate(LocalDateTime.now());
            fileMeta.setUserId(fileMetaDTO.getUserId());
            fileMeta.setDescription(fileMetaDTO.getDescription());

            fileMetaRepository.save(fileMeta);

            return fileMetaDTO;
        } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
        }
    }

    @Override
    public byte[] loadFile(String hash) {
        try {
            IPFS ipfs = ipfsConfig.ipfs;
            Multihash filePointer = Multihash.fromBase58(hash);
            return ipfs.cat(filePointer);
        } catch (IOException ex) {
            throw new RuntimeException("Error communicating with the IPFS node", ex);
        } catch (Exception ex) {
            // Handle generic exceptions
            throw new RuntimeException("Failed to load file from IPFS for hash: " + hash, ex);
        }
    }


//    @Override
//    public byte[] loadFile(String hash) {
//        try {
//            IPFS ipfs = ipfsConfig.ipfs;
//            Multihash filePointer = Multihash.fromBase58(hash);
//            return ipfs.cat(filePointer);
//        } catch (IOException ex) {
//            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
//        }
//    }

    @Override
    public FileMeta getFileMetaByCID(String cid) {
        Optional<FileMeta> fileMeta = fileMetaRepository.findByCID(cid);
        return fileMeta.orElseThrow(() -> new RuntimeException("File metadata not found for CID: " + cid));
    }

//    @Override
//    public List<Map<String, Object>> getFilesWithContentByUserId(Long userId) {
//        List<FileMeta> fileMetas = fileMetaRepository.findByUserId(userId);
//        return fileMetas.stream().map(fileMeta -> {
//            Map<String, Object> fileDetails = new HashMap<>();
//            fileDetails.put("fileMeta", fileMeta);
//            fileDetails.put("fileContent", loadFile(fileMeta.getCID()));
//            return fileDetails;
//        }).collect(Collectors.toList());
//    }

    @Override
    public List<FileMetaDTO> getFilesWithContentByUserId(Long userId) {
        List<FileMeta> fileMetas = fileMetaRepository.findByUserId(userId);
        return fileMetas.stream().map(fileMeta -> {

            FileMetaDTO fileMetaDTO = new FileMetaDTO();
            fileMetaDTO.setId(fileMeta.getId());
            fileMetaDTO.setFileName(fileMeta.getFileName());
            fileMetaDTO.setFileType(fileMeta.getFileType());
            fileMetaDTO.setFileSize(fileMeta.getFileSize());
            fileMetaDTO.setUserId(fileMeta.getUserId());
            fileMetaDTO.setDescription(fileMeta.getDescription());
            fileMetaDTO.setUploadDate(fileMeta.getUploadDate());
            fileMetaDTO.setCID(fileMeta.getCID());
            return fileMetaDTO;
        }).collect(Collectors.toList());
    }


    @Override
    public void shareFile(Long fileId, String recipientEmail) {
        FileMeta fileMeta = fileMetaRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("FileMeta not found with id: " + fileId));
//        ResponseEntity<Optional<UserCredential>> userCredential = userClient.getUserById(fileMeta.getUserId());
        emailService.sendFileUrlToRecipientEmail(fileMeta.getFileUrl(), recipientEmail);
    }

    @Override
    public void deleteFileById(Long id) {
        Optional<FileMeta> fileMetaOptional = fileMetaRepository.findById(id);
        if (fileMetaOptional.isPresent()) {
            FileMeta fileMeta = fileMetaOptional.get();
            try {
                IPFS ipfs = ipfsConfig.ipfs;
                Multihash filePointer = Multihash.fromBase58(fileMeta.getCID());
                ipfs.pin.rm(filePointer);
                ipfs.repo.gc();
            } catch (IOException ex) {
                throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
            }
            fileMetaRepository.deleteById(id);
        } else {
            throw new RuntimeException("FileMeta not found with id: " + id);
        }
    }
}
