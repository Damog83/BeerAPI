package com.dg.spring6restmvclombok.controllers;

import com.dg.spring6restmvclombok.entities.Beer;
import com.dg.spring6restmvclombok.model.BeerDTO;
import com.dg.spring6restmvclombok.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetList() {
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(3);
    }
    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBeers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetBeerById() {
        Beer testBeer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerController.getBeerByID(testBeer.getId());

        assertThat(testBeer).isNotNull();
        assertThat(testBeer.getId()).isEqualTo(beerDTO.getId());
    }

    @Test
    void testGetBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerByID(UUID.randomUUID());
        });
    }
}