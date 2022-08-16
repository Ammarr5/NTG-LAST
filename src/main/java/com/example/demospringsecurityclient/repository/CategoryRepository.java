package com.example.demospringsecurityclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demospringsecurityclient.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository <Category, Long>{

}
