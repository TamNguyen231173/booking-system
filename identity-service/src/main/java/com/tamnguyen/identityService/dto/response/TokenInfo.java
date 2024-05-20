package com.tamnguyen.identityService.dto.response;

import java.util.Date;

public record TokenInfo(String token, Date expiryDate) {}
