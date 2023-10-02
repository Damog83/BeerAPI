package com.dg.spring6restmvclombok.controllers;

import com.dg.spring6restmvclombok.services.BeerService;
import com.dg.spring6restmvclombok.services.BeerServiceImpl;
import com.dg.spring6restmvclombok.model.BeerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerDTOControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    BeerServiceImpl beerServiceImp;

    @BeforeEach
    void setUp() {
        beerServiceImp = new BeerServiceImpl();
    }
    @Test
    void getBeerByIDdNotFound() throws Exception {
        UUID testBeerId = UUID.randomUUID();

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeerId))
                .andExpect(status().isNotFound());
    }
    @Test
    void getBeerByID()  throws Exception{
        BeerDTO testBeerDTO = beerServiceImp.getBeerList().get(0);

        given(beerService.getBeerById(testBeerDTO.getId())).willReturn(Optional.of(testBeerDTO));

        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeerDTO.getId().toString())))
                .andExpect(jsonPath("$.version", is(testBeerDTO.getVersion())))
                .andExpect(jsonPath("$.beerName", is(testBeerDTO.getBeerName())))
                .andExpect(jsonPath("$.beerStyle", is(testBeerDTO.getBeerStyle().toString())))
                .andExpect(jsonPath("$.upc", is(testBeerDTO.getUpc())))
                .andExpect(jsonPath("$.quantityOnHand", is(testBeerDTO.getQuantityOnHand())))
                .andExpect(jsonPath("$.price", is(testBeerDTO.getPrice().doubleValue())));

        verify(beerService).getBeerById(uuidArgumentCaptor.capture());

        assertThat(testBeerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void getBeerList() throws Exception {
        given(beerService.getBeerList()).willReturn(beerServiceImp.getBeerList());

        mockMvc.perform(get(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void createNewBeer() throws Exception {
        BeerDTO testBeerDTO = beerServiceImp.getBeerList().get(0);
        testBeerDTO.setVersion(null);
        testBeerDTO.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImp.getBeerList().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateBeerByID() throws Exception {
        BeerDTO testBeerDTO = beerServiceImp.getBeerList().get(0);

        given(beerService.updateExistingBeer(any(UUID.class), any(BeerDTO.class))).willReturn(Optional.of(testBeerDTO));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, testBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeerDTO)))
                .andExpect(status().isNoContent());

        verify(beerService).updateExistingBeer(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(testBeerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(testBeerDTO.getBeerName()).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }
    @Test
    void deleteBeerById() throws Exception {
        BeerDTO testBeerDTO = beerServiceImp.getBeerList().get(0);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, testBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteBeer(uuidArgumentCaptor.capture());

        assertThat(testBeerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }
    @Test
    void updateBeerPatchId() throws Exception {
        BeerDTO testBeerDTO = beerServiceImp.getBeerList().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Beer Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, testBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

       verify(beerService).patchBeerByID(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

       assertThat(testBeerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
       assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

}