package com.tamnguyen.serviceaccount.repository;

import com.tamnguyen.serviceaccount.DTO.EmailDetails;

public interface EmailRepository {
  boolean sendEmail(EmailDetails emailDetails);
}
