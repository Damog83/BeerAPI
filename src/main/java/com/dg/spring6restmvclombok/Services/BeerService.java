package com.dg.spring6restmvclombok.Services;

import com.dg.spring6restmvclombok.model.Beer;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    Beer saveNewBeer(Beer beer);

    Beer getBeerById(UUID id);

    List<Beer> getBeerList();

    void updateExistingBeer(UUID id, Beer beer);

    void deleteBeer(UUID beerID);

}
