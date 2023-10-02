package com.dg.spring6restmvclombok.controllers;

import com.dg.spring6restmvclombok.entities.Beer;
import com.dg.spring6restmvclombok.mappers.BeerMapper;
import com.dg.spring6restmvclombok.model.BeerDTO;
import com.dg.spring6restmvclombok.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    BeerMapper beerMapper;

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
    @Rollback
    @Transactional
    @Test
    void testSaveBeer() {
        BeerDTO testBeer = BeerDTO.builder()
                           .beerName("Test Beer")
                           .build();

        ResponseEntity<BeerDTO> responseEntity = beerController.createNewBeer(testBeer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer savedBeer = beerRepository.findById(savedUUID).get();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getBeerName()).isEqualTo("Test Beer");
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateBeerById() {
        Beer testBeer = beerRepository.findAll().get(0);
        BeerDTO testBeerDTO = beerMapper.beerToBeerDto(testBeer);
        testBeerDTO.setId(null);
        testBeerDTO.setVersion(null);
        final String updatedName = "Updated";
        testBeerDTO.setBeerName(updatedName);

        ResponseEntity<BeerDTO> responseEntity =
                beerController.updateBeerById(testBeer.getId(),
                        testBeerDTO);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer =
                beerRepository.findById(testBeer.getId()).get();

        assertThat(updatedBeer.getBeerName()).isEqualTo(updatedName);
    }

    @Test
    void testUpdateBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateBeerById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }
}
