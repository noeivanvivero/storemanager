package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.Inventory;

public interface IRepositoryInventory extends JpaRepository<Inventory, Long> {

}
