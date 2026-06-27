package com.cityskill.ebanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityskill.ebanking.entite.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
