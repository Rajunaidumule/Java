package com.td.productCatalog.controller;

import com.td.controller.ProductsApi;
import com.td.model.Product;
import com.td.productCatalog.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCatalogController implements ProductsApi {
    @Autowired
    ProductCatalogService service;
    @Override
    public ResponseEntity<Void> productsCreateProductPost(@RequestBody Product product){
        service.createProduct(product);
        return ResponseEntity.ok().build();
    }
}
