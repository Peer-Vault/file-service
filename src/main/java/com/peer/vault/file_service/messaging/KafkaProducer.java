package com.peer.vault.file_service.messaging;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.peer.vault.file_service.util.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void producerForFileSharing(String fileUrl, String recipientEmail) {
        try {
            Map<String, String> fileData = new HashMap<>();
            fileData.put("fileUrl", fileUrl);
            fileData.put("email", recipientEmail);
            String file = objectMapper.writeValueAsString(fileData);
            kafkaTemplate.send(KafkaConstants.File_SHARED, file);
            System.out.println("File sent to kafka topic: " + recipientEmail);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
