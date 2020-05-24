package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.AdjustmentType;

public interface IRepositoryAdjustmentType extends JpaRepository<AdjustmentType, Long> {

}
