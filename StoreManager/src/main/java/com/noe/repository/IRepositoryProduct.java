package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.Product;

public interface IRepositoryProduct extends JpaRepository<Product,Long> {

}
