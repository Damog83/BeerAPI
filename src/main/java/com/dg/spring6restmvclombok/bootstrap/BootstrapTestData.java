package com.dg.spring6restmvclombok.bootstrap;

import com.dg.spring6restmvclombok.entities.Beer;
import com.dg.spring6restmvclombok.entities.Customer;
import com.dg.spring6restmvclombok.model.BeerStyle;
import com.dg.spring6restmvclombok.repositories.BeerRepository;
import com.dg.spring6restmvclombok.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class BootstrapTestData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        loadBeerData();
        loadCustomerData();


//        beerMap.put(beerDTO1.getId(), beerDTO1);
//        beerMap.put(beerDTO2.getId(), beerDTO2);
//        beerMap.put(beerDTO3.getId(), beerDTO3);


    }

    private void loadBeerData() {
        Beer beer1 = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
    }

    private void loadCustomerData() {
        Customer customer1 = Customer.builder()
                .name("Bobby")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .name("Ellie")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .name("Damien")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
    }
}
