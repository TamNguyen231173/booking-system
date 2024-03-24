package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Vender;

public interface VenderRepository extends MongoRepository<Vender, String> { 
  
}
