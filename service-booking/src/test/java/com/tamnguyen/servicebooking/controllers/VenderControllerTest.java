package com.tamnguyen.servicebooking.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamnguyen.servicebooking.DTOs.Venders.AddVenderDTO;
import com.tamnguyen.servicebooking.DTOs.Venders.UpdateVenderDTO;
import com.tamnguyen.servicebooking.enums.ServiceType;
import com.tamnguyen.servicebooking.models.Vender;
import com.tamnguyen.servicebooking.services.VenderService;

@WebMvcTest(VenderController.class)
public class VenderControllerTest {
  private static final String VENDER_ENDPOINT = "/api/v1/vender";
  private static final String GET_ALL_VENDERS_ENDPOINT = VENDER_ENDPOINT + "/get-all";
  private static final String ADD_VENDER_ENDPOINT = VENDER_ENDPOINT + "/add";
  private static final String GET_VENDER_ENDPOINT = VENDER_ENDPOINT + "/{id}/get-by-id";
  private static final String UPDATE_VENDER_ENDPOINT = VENDER_ENDPOINT + "/{id}";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private VenderService venderService;

  private Vender vender1;
  private Vender vender2;
  private AddVenderDTO addVenderDTO;
  private UpdateVenderDTO updateVenderDTO;

  @BeforeEach
  public void setUp() {
    addVenderDTO = new AddVenderDTO();
    addVenderDTO.setName("Tam Vender");
    addVenderDTO.setAddress("123 ABC Street");
    addVenderDTO.setPhone("1234567890");
    addVenderDTO.setEmail("tam@vender.com");
    addVenderDTO.setService(ServiceType.HOTEL);

    updateVenderDTO = new UpdateVenderDTO();
    updateVenderDTO.setName("Tam Vender 2");
    updateVenderDTO.setAddress("456 EDF Street");
    updateVenderDTO.setService(ServiceType.FLIGHT);


    vender1 = new Vender();
    vender1.setId("1");
    vender1.setName(addVenderDTO.getName());
    vender1.setAddress(addVenderDTO.getAddress());
    vender1.setPhone(addVenderDTO.getPhone());
    vender1.setEmail(addVenderDTO.getEmail());
    vender1.setService(addVenderDTO.getService());

    vender2 = new Vender();
    vender2.setId("2");
    vender2.setName(updateVenderDTO.getName());
    vender2.setAddress(updateVenderDTO.getAddress());
    vender2.setPhone("89808345");
    vender2.setEmail("tam@vender2.com");
    vender2.setService(updateVenderDTO.getService());
  }

  @Test
  public void testGetAllVenders() throws Exception {
    when(venderService.getVenders()).thenReturn(Arrays.asList(vender1, vender2));

    mockMvc.perform(MockMvcRequestBuilders.get(GET_ALL_VENDERS_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(2)));
  }

  @Test
  public void testAddVender() throws Exception {
    when(venderService.addVender(any(AddVenderDTO.class))).thenReturn(vender1);

    mockMvc.perform(MockMvcRequestBuilders.post(ADD_VENDER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(new ObjectMapper().writeValueAsString(addVenderDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is("1")))
            .andExpect(jsonPath("$.data.name", is(addVenderDTO.getName())))
            .andExpect(jsonPath("$.data.address", is(addVenderDTO.getAddress())))
            .andExpect(jsonPath("$.data.phone", is(addVenderDTO.getPhone())))
            .andExpect(jsonPath("$.data.email", is(addVenderDTO.getEmail())))
            .andExpect(jsonPath("$.data.service", is(addVenderDTO.getService().toString())));
  }

  @Test
  public void testGetVender() throws Exception {

    when(venderService.getVender(vender1.getId())).thenReturn(vender1);

    mockMvc.perform(MockMvcRequestBuilders.get(GET_VENDER_ENDPOINT, vender1.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is("1")))
            .andExpect(jsonPath("$.data.name", is(vender1.getName())))
            .andExpect(jsonPath("$.data.address", is(vender1.getAddress())))
            .andExpect(jsonPath("$.data.phone", is(vender1.getPhone())))
            .andExpect(jsonPath("$.data.email", is(vender1.getEmail())))
            .andExpect(jsonPath("$.data.service", is(vender1.getService().toString())));
  }

  @Test
  public void testUpdateVender() throws Exception {
    when(venderService.updateVender(vender2.getId(), updateVenderDTO)).thenReturn(vender2);

    mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_VENDER_ENDPOINT, vender2.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(new ObjectMapper().writeValueAsString(updateVenderDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id", is(vender2.getId())))
            .andExpect(jsonPath("$.data.name", is(updateVenderDTO.getName())))
            .andExpect(jsonPath("$.data.address", is(updateVenderDTO.getAddress())))
            .andExpect(jsonPath("$.data.service", is(updateVenderDTO.getService().toString())));
  }

  @Test
  public void testDeleteVender() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete(UPDATE_VENDER_ENDPOINT, "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
  }
}
