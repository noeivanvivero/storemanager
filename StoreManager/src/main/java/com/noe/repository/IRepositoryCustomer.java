package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.Customer;

public interface IRepositoryCustomer extends JpaRepository<Customer, Long> {

}
