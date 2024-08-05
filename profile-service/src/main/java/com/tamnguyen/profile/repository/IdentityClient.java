package com.tamnguyen.profile.repository;

import com.tamnguyen.profile.dto.identity.TokenExchangeParam;
import com.tamnguyen.profile.dto.identity.TokenExchangeResponse;
import com.tamnguyen.profile.dto.identity.UserCreationParam;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(value = "/realms/tamnh/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);

    @PostMapping(value = "/admin/realms/tamnh/users",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> createUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UserCreationParam param);
}
