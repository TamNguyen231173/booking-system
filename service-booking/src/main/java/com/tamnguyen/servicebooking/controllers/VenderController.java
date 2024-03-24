package com.tamnguyen.servicebooking.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tamnguyen.servicebooking.DTOs.ResponseSuccess;
import com.tamnguyen.servicebooking.DTOs.Venders.AddVenderDTO;
import com.tamnguyen.servicebooking.DTOs.Venders.UpdateVenderDTO;
import com.tamnguyen.servicebooking.models.Vender;
import com.tamnguyen.servicebooking.services.VenderService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1/vender")
@RequiredArgsConstructor
public class VenderController {
  private final VenderService venderService;

  @GetMapping("/get-all")
  public ResponseEntity<ResponseSuccess> getAllVenders() {
    List<Vender> venders = venderService.getVenders();

    return ResponseEntity.ok(
      ResponseSuccess.builder()
        .status("success")
        .message("Get all venders successfully")
        .data(venders)
        .build()
    );
  }

  @PostMapping("/add")
  public ResponseEntity<ResponseSuccess> addVender(@RequestBody AddVenderDTO request) {
      Vender vender = venderService.addVender(request);
      
      return ResponseEntity.ok(
        ResponseSuccess.builder()
          .status("success")
          .message("Add vender successfully")
          .data(vender)
          .build()
      );
  }
  
  @GetMapping("/{id}/get-by-id")
  public ResponseEntity<ResponseSuccess> getVender(@PathVariable String id) {
      Vender vender = venderService.getVender(id);

      return ResponseEntity.ok(
        ResponseSuccess.builder()
          .status("success")
          .message("Get vender successfully")
          .data(vender)
          .build()
      );
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseSuccess> updateVender(@PathVariable String id, @RequestBody UpdateVenderDTO request) {
      Vender vender = venderService.updateVender(id, request);

      return ResponseEntity.ok(
        ResponseSuccess.builder()
          .status("success")
          .message("Update vender successfully")
          .data(vender)
          .build()
      );
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseSuccess> deleteVender(@PathVariable String id) {
      venderService.deleteVender(id);

      return ResponseEntity.ok(
        ResponseSuccess.builder()
          .status("success")
          .message("Delete vender successfully")
          .build()
      );
  }
}
