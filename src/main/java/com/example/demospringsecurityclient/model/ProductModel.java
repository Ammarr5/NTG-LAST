package com.example.demospringsecurityclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    String name;
    String imageURL;
    float price;
    String description;
    int boughtItemsCount;
    String category;
}
