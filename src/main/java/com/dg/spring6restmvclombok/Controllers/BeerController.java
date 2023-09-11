package com.dg.spring6restmvclombok.Controllers;

import com.dg.spring6restmvclombok.Services.BeerService;
import com.dg.spring6restmvclombok.model.Beer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity handlePost(@RequestBody Beer beer) {

        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
}