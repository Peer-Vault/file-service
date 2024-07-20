package com.peer.vault.file_service.repository;

import com.peer.vault.file_service.domain.FileMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileMetaRepository extends JpaRepository<FileMeta, Long> {
    Optional<FileMeta> findByCID(String cid);

    List<FileMeta> findByUserId(Long userId);
}
