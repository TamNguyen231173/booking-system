package com.tamnguyen.profile.repository.httpclient;

import com.tamnguyen.profile.dto.request.EmailRequest;
import com.tamnguyen.profile.dto.response.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://api.brevo.com")
public interface EmailClient {
    @PostMapping(value = "/v1/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
    EmailResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody EmailRequest body);
}
