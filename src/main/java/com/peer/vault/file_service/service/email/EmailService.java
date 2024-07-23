package com.peer.vault.file_service.service.email;

import com.peer.vault.file_service.client.NotificationClient;
import com.peer.vault.file_service.domain.UserCredential;
import com.peer.vault.file_service.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private NotificationClient notificationClient;

    public void sendFileUrlToRecipientEmail(String fileUrl, String recipientEmail) {
        String emailBody = String.format(
                "Dear User,\n\n" +
                        "You have received a file.\n\n" +
                        "Here is the file URL: %s\n\n" +
                        "If you have any questions or need further assistance, please feel free to reach out to our support team.\n\n" +
                        "Best regards,\n\n" +
                        "The Peer Vault Team\n" +
                        "Contact: 8955946276\n" +
                        "Email: cmanishkumar193@gmail.com",
                fileUrl);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(recipientEmail);
        emailRequest.setSubject("You have received a file");
        emailRequest.setBody(emailBody);

        notificationClient.sendEmail(emailRequest);
    }



}
