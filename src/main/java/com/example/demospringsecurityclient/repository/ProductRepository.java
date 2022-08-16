package com.example.demospringsecurityclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demospringsecurityclient.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository <Product, Long>{

}
