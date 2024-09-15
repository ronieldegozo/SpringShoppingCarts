package com.shoppingcart.shoppingcarts.service.product;

import com.shoppingcart.shoppingcarts.model.Product;
import com.shoppingcart.shoppingcarts.request.AddProductRequest;
import com.shoppingcart.shoppingcarts.request.ProductUpdateRequest;

import java.util.List;

public interface InterfaceProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName (String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

}