package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.Sale;

public interface IRepositorySale extends JpaRepository<Sale, Long> {

}
