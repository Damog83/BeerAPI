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
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> getBeerList() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
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
