package com.noe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noe.data.Employee;

public interface IRepositoryEmployee extends JpaRepository<Employee, Long> {

}
