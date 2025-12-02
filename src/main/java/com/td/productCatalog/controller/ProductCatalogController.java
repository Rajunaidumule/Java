package com.td.productCatalog.controller;

import com.td.controller.ProductsApi;
import com.td.model.Product;
import com.td.productCatalog.process.ProductCatalogProcessor;
import com.td.productCatalog.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductCatalogController implements ProductsApi {
    @Autowired
    ProductCatalogProcessor processor;


    @Override
    public ResponseEntity<Void> productsCreateProductPost(@RequestBody Product product){
        processor.createProduct(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        System.out.println("request came to get all products");
        return ResponseEntity.ok(processor.getAllProducts());
    }
}
