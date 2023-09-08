package com.dg.spring6restmvclombok.Controllers;

import com.dg.spring6restmvclombok.Services.BeerService;
import com.dg.spring6restmvclombok.model.Beer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @GetMapping()
    public List<Beer> listBeers() {
        return beerService.getBeerList();
    }

    @GetMapping("/{beerID}")
    public Beer getBeerByID(@PathVariable("beerID") UUID beerId) {
        log.debug("getBeerByID method called in BeerController");
        return beerService.getBeerById(beerId);
    }
}