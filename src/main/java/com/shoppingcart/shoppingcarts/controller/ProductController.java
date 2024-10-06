package com.shoppingcart.shoppingcarts.controller;

import com.shoppingcart.shoppingcarts.dto.ProductDto;
import com.shoppingcart.shoppingcarts.exceptions.ProductNotFoundException;
import com.shoppingcart.shoppingcarts.exceptions.ResouseNotFoundException;
import com.shoppingcart.shoppingcarts.model.Product;
import com.shoppingcart.shoppingcarts.request.AddProductRequest;
import com.shoppingcart.shoppingcarts.request.ProductUpdateRequest;
import com.shoppingcart.shoppingcarts.response.ApiResponse;
import com.shoppingcart.shoppingcarts.service.product.InterfaceProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final InterfaceProductService productService;

    @GetMapping("/all")
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

    @GetMapping("/product/{productId}/product")
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


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct (@RequestBody AddProductRequest product){
        try {
            Product products = productService.addProduct(product);
            return  ResponseEntity.ok(new ApiResponse("Add Product Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Cannot Add Product!", null));
        }
    }


    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct (@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
        try {
            Product updateProduct = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Update Product Success!", updateProduct));
        } catch (ResouseNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct (@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product Deleted!", null));
        } catch (ResouseNotFoundException e) {
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


    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand (@PathVariable String category, @PathVariable String productId, @PathVariable String brand){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category,brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Get Product Category and Brand -> Product Not Found!", null));
            }
            return  ResponseEntity.ok(new ApiResponse("Product by Category and Brand Found!", products));

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


    @GetMapping("/product/by-brand")
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

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductByCategory (@PathVariable String category){
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

    @GetMapping("/product/count/by-brand/and-name")
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
