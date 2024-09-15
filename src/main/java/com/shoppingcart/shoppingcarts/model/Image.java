package com.shoppingcart.shoppingcarts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {

    //Automatic Generated Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;
    private String fileType;

    @Lob
    private Blob image;
    private String downloadUrl;

    //Many Images belong to one Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;



}
