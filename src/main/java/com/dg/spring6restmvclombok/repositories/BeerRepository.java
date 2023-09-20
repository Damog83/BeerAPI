package com.dg.spring6restmvclombok.repositories;

import com.dg.spring6restmvclombok.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
