package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Leg;

public interface LegRepository extends MongoRepository<Leg, String> {
  
}
