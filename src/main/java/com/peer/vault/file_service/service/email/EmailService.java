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

    public void sendFileUrlToRecipientEmail(String fileUrl, String recipientEmail, ResponseEntity<Optional<UserCredential>> responseEntity) {
        Optional<UserCredential> optionalUserCredential = responseEntity.getBody();
        if (optionalUserCredential.isPresent()) {
            UserCredential senderCredential = optionalUserCredential.get();
            String senderName = senderCredential.getFirstName() + " " + senderCredential.getLastName();
            String senderEmail = senderCredential.getEmail();

            String emailBody = String.format(
                    "Dear User,\n\n" +
                            "You have received a file from %s.\n\n" +
                            "Here is the file URL: %s\n\n" +
                            "If you have any questions or need further assistance, please feel free to contact the sender directly or reach out to our support team.\n\n" +
                            "Sender Details:\n" +
                            "Name: %s\n" +
                            "Email: %s\n\n\n" +
                            "Best regards,\n\n" +
                            "The Peer Vault Team\n" +
                            "Contact: 8955946276\n" +
                            "Email: cmanishkumar193@gmail.com",
                    senderName, fileUrl,
                    senderName, senderEmail);

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(recipientEmail);
            emailRequest.setSubject(String.format("You have received a file from %s", senderName));
            emailRequest.setBody(emailBody);

            notificationClient.sendEmail(emailRequest);
        } else {
            throw new RuntimeException("Sender's credentials are not available.");
        }
    }


}
