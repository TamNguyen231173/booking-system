package com.tamnguyen.profile.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamnguyen.profile.dto.identity.KeyCloakError;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ErrorNormalizer {
    private final Map<String, ErrorCode> errorCodeMap;
    private final ObjectMapper objectMapper;

    public ErrorNormalizer() {
        objectMapper = new ObjectMapper();
        errorCodeMap = new HashMap<>();

        for (ErrorCode errorCode : ErrorCode.values()) {
            errorCodeMap.put(errorCode.name(), errorCode);
        }
    }

    public AppException handleKeyCloakException(FeignException e) {
        try {
            log.warn("Can not complete", e.contentUTF8());
            var response = objectMapper.readValue(e.contentUTF8(), KeyCloakError.class);

            if (Objects.nonNull(response.getErrorMessage()) && Objects.nonNull(errorCodeMap.get(response.getErrorMessage()))) {
                return new AppException(errorCodeMap.get(response.getErrorMessage()));
            }
        } catch (JsonProcessingException ex) {
            log.error("Error parsing keycloak error", ex);
        }
        return new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
}
