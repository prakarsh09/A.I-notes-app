package com.example.notesapp.entity;

import java.io.Serializable;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="notes")

public class Notes extends Audit implements Serializable,Comparable<Notes>{
    
 @Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
@Column(nullable=false)
private String title;
@Column(nullable=false,length = 65555)
private String content;

@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="user_id",nullable=false)
@OnDelete(action=OnDeleteAction.CASCADE)
private Employee empl;

@Override
public int compareTo(Notes that) {
   return this.id-that.id;
}

}