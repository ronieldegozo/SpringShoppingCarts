package com.shoppingcart.shoppingcarts.controller;

import com.shoppingcart.shoppingcarts.dto.ProductDto;
import com.shoppingcart.shoppingcarts.exceptions.AlreadyExistsException;
import com.shoppingcart.shoppingcarts.exceptions.ProductNotFoundException;
import com.shoppingcart.shoppingcarts.exceptions.ResourceNotFoundException;
import com.shoppingcart.shoppingcarts.model.Product;
import com.shoppingcart.shoppingcarts.request.AddProductRequest;
import com.shoppingcart.shoppingcarts.request.ProductUpdateRequest;
import com.shoppingcart.shoppingcarts.response.ApiResponse;
import com.shoppingcart.shoppingcarts.service.product.InterfaceProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final InterfaceProductService productService;

    /**
     * Get All Products
     * Endpoint: http://localhost:5000/rest/v1/products
     * @return ResponseEntity containing a list of products
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts (){
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", convertedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Can't find List of Products", e.getMessage()));
        }
    }

    /**
     * Get product byId
     * Endpoint: http://localhost:5000/rest/v1/products/<>productId>
     * @return ResponseEntity containing a specific product
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById (@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return  ResponseEntity.ok(new ApiResponse("Product Id Found!", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Add a New Product
     * Endpoint: http://localhost:5000/rest/v1/products
     * @param product Product data
     * @return ResponseEntity with the status of the operation
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')") // only admin can add the product
    @PostMapping
    public ResponseEntity<ApiResponse> addProduct (@RequestBody AddProductRequest product){
        try {
            Product createdProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Add Product Success!", createdProduct));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')") // only admin can update the product
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct (@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
        try {
            Product updateProduct = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Update Product Success!", updateProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')") // only admin can delete the product
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct (@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product Deleted!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by/brandname-and-productname")
    public ResponseEntity<ApiResponse> getProductByBrandAndName (@RequestParam String brandname, @RequestParam String productname){
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandname,productname);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                       .body(new ApiResponse("Get Product Brand and Name -> Product Not Found!", null));
            }
            return  ResponseEntity.ok(new ApiResponse("Product by Brand and Name Found!", products));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    /*
    Get Product By Category and Brand
    http://localhost:5000/rest/v1/products/product/TV?brand=Samsung
    */
    @GetMapping("/product/{category}")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand (@PathVariable String category, @RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category,brand);
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            if(convertedProduct.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Get Product Category and Brand -> Product Not Found!", null));
            }
            return  ResponseEntity.ok(new ApiResponse("Product by Category and Brand Found!", convertedProduct));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/product/{productName}/products")
    public ResponseEntity<ApiResponse> getProductByName (@PathVariable String brandName){
        try {
            List<Product> products = productService.getProductsByName(brandName);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Get Product by Name -> Product Not Found!", null));
            }
            return  ResponseEntity.ok(new ApiResponse("Product by Name Found!", products));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    /*
    Get Product By Brand
    http://localhost:5000/rest/v1/products/product?brand=Ios
    */
    @GetMapping("/product")
    public ResponseEntity<ApiResponse> getProductByBrand (@RequestParam String brand){
        try {
            List<Product> products = productService.getProductByBrand(brand);
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            if(convertedProduct.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Get Product by brand -> Product Not Found!", null));
            }
            return  ResponseEntity.ok(new ApiResponse("Get Product by brand Found!", convertedProduct));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Get all products base on category name
     * Endpoint: http://localhost:5000/rest/v1/products/category/gadgets
     * @param category
     * @return ResponseEntity containing a specific product
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse> getProductByCategoryName (@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            if(convertedProduct.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Get Product By Category -> Product Not Found!", null));
            }
            return  ResponseEntity.ok(new ApiResponse("Get Product by Category Found!", convertedProduct));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    /**
     * Get total count of Product by brand and name
     * Endpoint: http://localhost:5000/rest/v1/products/count?brand=ios&name=Iphone 15
     * @param brand
     * @param name
     * @return ResponseEntity containing a specific product
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')") // only admin can count the product
    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName (@RequestParam String brand, @RequestParam String name){
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return  ResponseEntity.ok(new ApiResponse("Get Product by Category Found!", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
