package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.Tax;

public interface IRepositoryTax extends JpaRepository<Tax,Long> {

}
