package com.shoppingcart.shoppingcarts.dto;

import com.shoppingcart.shoppingcarts.model.Category;
import com.shoppingcart.shoppingcarts.model.Image;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    //Automatic Generated Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    private Category category;

    private List<ImageDto> images;

}
