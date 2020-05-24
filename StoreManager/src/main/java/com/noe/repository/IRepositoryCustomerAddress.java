package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.CustomerAddress;

public interface IRepositoryCustomerAddress extends JpaRepository<CustomerAddress, Long> {

}
