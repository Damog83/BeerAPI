package com.dg.spring6restmvclombok.services;

import com.dg.spring6restmvclombok.mappers.BeerMapper;
import com.dg.spring6restmvclombok.model.BeerDTO;
import com.dg.spring6restmvclombok.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<BeerDTO> getBeerList() {
        return null;
    }

    @Override
    public void updateExistingBeer(UUID id, BeerDTO beerDTO) {

    }

    @Override
    public void deleteBeer(UUID beerID) {

    }

    @Override
    public void patchBeerByID(UUID beerID, BeerDTO beerDTO) {

    }
}
