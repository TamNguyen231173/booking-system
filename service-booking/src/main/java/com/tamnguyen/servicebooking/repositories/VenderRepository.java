package com.tamnguyen.servicebooking.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Vender;

public interface VenderRepository extends MongoRepository<Vender, String> { 
  List<Vender> findByIsDeletedFalse();

  Vender findByIdAndIsDeletedFalse(String id);
}
