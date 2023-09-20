package com.dg.spring6restmvclombok.repositories;

import com.dg.spring6restmvclombok.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
