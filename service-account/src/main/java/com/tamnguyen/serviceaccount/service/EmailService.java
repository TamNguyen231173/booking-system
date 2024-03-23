package com.tamnguyen.serviceaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tamnguyen.serviceaccount.DTO.EmailDetails;
import com.tamnguyen.serviceaccount.repository.EmailRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailRepository {

  @Autowired private JavaMailSender javaMailSender;
 
  @Value("${mail.from}") private String sender;
   
  @SuppressWarnings("null")
  public boolean sendEmail(EmailDetails emailDetails) {
      try {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(sender);
        helper.setTo(emailDetails.getRecipient());
        helper.setSubject(emailDetails.getSubject());
        helper.setText(emailDetails.getMsgBody(), true);

        if (emailDetails.getAttachment() != null) {
          FileSystemResource file = new FileSystemResource(emailDetails.getAttachment());
          helper.addAttachment(file.getFilename(), file);
        }

        javaMailSender.send(mimeMessage);

        return true;
      } catch (Exception e) {
          e.printStackTrace();
          return false;
      }
  } 
}
