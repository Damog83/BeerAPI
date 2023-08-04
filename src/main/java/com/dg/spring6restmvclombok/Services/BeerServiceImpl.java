package com.dg.spring6restmvclombok.Services;

import com.dg.spring6restmvclombok.model.Beer;
import com.dg.spring6restmvclombok.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerById(UUID id) {
        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Staffy Plumb")
                .beerStyle(BeerStyle.ALE)
                .upc("123455")
                .quantityOnHand(100)
                .price(new BigDecimal(4.99))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
