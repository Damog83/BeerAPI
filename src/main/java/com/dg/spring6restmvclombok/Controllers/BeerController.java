package com.dg.spring6restmvclombok.Controllers;

import com.dg.spring6restmvclombok.Services.BeerService;
import com.dg.spring6restmvclombok.model.Beer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";


    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public List<Beer> listBeers() {
        log.debug("listBeers method called in BeerController");
        return beerService.getBeerList();
    }

    @GetMapping(BEER_PATH_ID)
    public Beer getBeerByID(@PathVariable("beerId") UUID beerId) {
        log.debug("getBeerByID method called in BeerController");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<String> createNewBeer(@RequestBody Beer beer) {
        log.debug("createNewBeer method called in BeerController");
        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<String> updateBeerById(@PathVariable("beerId") UUID beerID, @RequestBody Beer beer) {
        log.debug("updateBeerById method called in BeerController");
        beerService.updateExistingBeer(beerID, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<String> deleteBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("deleteBeerById method called in BeerController");
        beerService.deleteBeer(beerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<String> updateBeerPatchById(@PathVariable("beerId") UUID beerID, @RequestBody Beer beer) {
        log.debug("updateBeerPatchById method called in BeerController");
        beerService.patchBeerByID(beerID, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}