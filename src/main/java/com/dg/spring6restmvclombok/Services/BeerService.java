package com.dg.spring6restmvclombok.Services;

import com.dg.spring6restmvclombok.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);

    List<Beer> getBeerList();
}
