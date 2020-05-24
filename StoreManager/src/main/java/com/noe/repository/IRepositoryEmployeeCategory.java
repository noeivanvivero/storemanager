package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.EmployeeCategory;

public interface IRepositoryEmployeeCategory extends JpaRepository<EmployeeCategory, Long> {

}
