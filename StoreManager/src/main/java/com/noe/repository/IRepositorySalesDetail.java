package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.SalesDetail;

public interface IRepositorySalesDetail extends JpaRepository<SalesDetail, Long> {

}
