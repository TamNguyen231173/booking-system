package com.tamnguyen.serviceaccount.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tamnguyen.serviceaccount.model.Token;
import com.tamnguyen.serviceaccount.enums.TokenType;


public interface TokenRepository extends JpaRepository<Token, Long> {

  @Query(value = """
      select t from Token t inner join Account acc\s
      on t.account.id = acc.id\s
      where acc.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Long id);

  Optional<Token> findByToken(String token);

  Optional<Token> findByTokenAndType(String token, TokenType type);

  Token findByAccountId(Long accountId);

  void deleteByToken(String token);

  void deleteByAccountId(Long accountId);
}
