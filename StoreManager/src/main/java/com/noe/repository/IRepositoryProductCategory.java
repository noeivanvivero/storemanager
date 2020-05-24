package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.ProductCategory;

public interface IRepositoryProductCategory extends JpaRepository<ProductCategory, Long> {

}
