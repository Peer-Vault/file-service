package com.peer.vault.file_service.client;

import com.peer.vault.file_service.domain.UserCredential;
import com.peer.vault.file_service.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "userservice", url = "http://localhost:9091/user")
public interface UserClient {
    @GetMapping("/userinfo/getUserById")

    ResponseEntity<Optional<UserCredential>> getUserById(@RequestParam("id") Long id);

}
