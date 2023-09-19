package com.dg.spring6restmvclombok.Services;

import com.dg.spring6restmvclombok.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    BeerDTO saveNewBeer(BeerDTO beerDTO);
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> getBeerList();

    void updateExistingBeer(UUID id, BeerDTO beerDTO);

    void deleteBeer(UUID beerID);

    void patchBeerByID(UUID beerID, BeerDTO beerDTO);
}
