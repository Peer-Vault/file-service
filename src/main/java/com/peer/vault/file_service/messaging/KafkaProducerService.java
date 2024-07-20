package com.peer.vault.file_service.messaging;

import com.peer.vault.file_service.constant.KafkaConstant;
import com.peer.vault.file_service.dto.FileShareEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {


    @Autowired
    private KafkaTemplate<String, FileShareEvent> kafkaTemplate;

    public void sendFileShareEvent(String fileUrl, String email) {
        FileShareEvent event = new FileShareEvent(fileUrl, email);
        kafkaTemplate.send(KafkaConstant.FILE_SHARED_TOPIC, event);
    }

}
