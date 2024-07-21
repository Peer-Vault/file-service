package com.peer.vault.file_service.client;

import com.peer.vault.file_service.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationservice", url = "http://localhost:9090/notifications")
public interface NotificationClient {
    @PostMapping("/sendEmail")
    void sendEmail(@RequestBody EmailRequest emailRequest);

}
