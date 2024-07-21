package com.tamnguyen.profile.service;

import com.tamnguyen.profile.dto.request.EmailRequest;
import com.tamnguyen.profile.dto.request.SendEmailRequest;
import com.tamnguyen.profile.dto.request.Sender;
import com.tamnguyen.profile.dto.response.EmailResponse;
import com.tamnguyen.profile.exception.AppException;
import com.tamnguyen.profile.exception.ErrorCode;
import com.tamnguyen.profile.repository.httpclient.EmailClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @NonFinal
    @Value("${brevo.apiKey}")
    String brevoApiKey;

    @NonFinal
    @Value("${spring.emailDefault}")
    String emailDefault;

    @NonFinal
    @Value("${spring.nameEmail}")
    String nameEmail;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder().email(emailDefault).name(nameEmail).build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(brevoApiKey, emailRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
