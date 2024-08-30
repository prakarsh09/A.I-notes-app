package com.example.notesapp.entity;

import java.io.Serializable;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="user google")
public class Employee implements Serializable{
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
   
    private String email;
   
}
