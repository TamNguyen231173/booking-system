package com.tamnguyen.servicebooking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tamnguyen.servicebooking.DTOs.Venders.AddVenderDTO;
import com.tamnguyen.servicebooking.DTOs.Venders.UpdateVenderDTO;
import com.tamnguyen.servicebooking.models.Vender;
import com.tamnguyen.servicebooking.repositories.VenderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenderService {
  private final VenderRepository venderRepository;

  public List<Vender> getVenders() {
    return venderRepository.findByIsDeletedFalse();
  }

  public Vender getVender(String venderId) {
    Vender vender = venderRepository.findByIdAndIsDeletedFalse(venderId);

    if (vender == null) {
      throw new RuntimeException("Vender not found");
    }
    
    return vender;
  }

  public Vender addVender(AddVenderDTO payload) {
    Vender newVender = Vender.builder()
      .name(payload.getName())
      .address(payload.getAddress())
      .phone(payload.getPhone())
      .email(payload.getEmail())
      .service(payload.getService())
      .build();

    return venderRepository.save(newVender);
  }

  public Vender updateVender(String venderId, UpdateVenderDTO payload) {
    Vender vender =  venderRepository.findById(venderId)
      .orElseThrow(() -> new RuntimeException("Vender not found"));

    vender.setName(payload.getName());
    vender.setAddress(payload.getAddress());
    vender.setService(payload.getService());

    venderRepository.save(vender);

    return vender;
  }

  public void deleteVender(String venderId) {
    Vender vender = venderRepository.findById(venderId)
      .orElseThrow(() -> new RuntimeException("Vender not found"));

    vender.setIsDeleted(true);

    venderRepository.save(vender);
  }
}
