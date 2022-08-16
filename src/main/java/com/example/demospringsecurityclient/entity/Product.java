package com.example.demospringsecurityclient.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	    @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Column(name="name",nullable = false,unique = true)
	    private String name;
	    @Column(name="imageurl",nullable = false)
	    private String imageurl;
	    @Column(name="price",nullable = false)
	    private float price;
	    @Column(name="description",nullable = false)
	    private String description;
	    
	   
	    @ManyToOne
	    @JoinColumn(name="category_id", nullable = false)
	    Category category;
}
