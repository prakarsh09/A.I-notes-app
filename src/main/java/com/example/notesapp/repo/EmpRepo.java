package com.example.notesapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notesapp.entity.Employee;

public interface EmpRepo extends JpaRepository<Employee, Integer>{
    public Employee findByEmail(String email);

}
