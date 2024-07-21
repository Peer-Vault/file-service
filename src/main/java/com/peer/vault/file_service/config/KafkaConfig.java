//package com.peer.vault.file_service.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class KafkaConfig {
//
//    public static final String FILE_SHARED_TOPIC = "FILE_SHARED_TOPIC";
//
//    @Bean
//    public NewTopic fileSharedTopic() {
//        return new NewTopic(FILE_SHARED_TOPIC, 1, (short) 1);
//    }
//}