//package com.peer.vault.file_service.messaging;
//
//import com.peer.vault.file_service.config.KafkaConfig;
//import com.peer.vault.file_service.dto.FileShareEvent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FileShareProducerService {
//    private final KafkaTemplate<String, FileShareEvent> kafkaTemplate;
//
//    @Autowired
//    public FileShareProducerService(KafkaTemplate<String, FileShareEvent> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendFileShareEvent(FileShareEvent event) {
//        kafkaTemplate.send("FILE_SHARED_TOPIC", event);
//    }
//}
