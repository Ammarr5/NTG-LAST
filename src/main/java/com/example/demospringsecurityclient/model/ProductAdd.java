package com.example.demospringsecurityclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductAdd {
	private String name;
	private String imageurl;
	  private float price;
	  private String description;
	  private Long category_id;
}
