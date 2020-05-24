package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.InventoryAdjustment;

public interface IRepositoryInventoryAdjustment extends JpaRepository<InventoryAdjustment, Long> {

}
