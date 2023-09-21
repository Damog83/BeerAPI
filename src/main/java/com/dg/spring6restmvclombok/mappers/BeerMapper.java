package com.dg.spring6restmvclombok.mappers;

import com.dg.spring6restmvclombok.entities.Beer;
import com.dg.spring6restmvclombok.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
