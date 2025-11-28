package com.td.productCatalog.controller;

import com.td.controller.ProductsApi;
import com.td.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCatalogController implements ProductsApi {
    @Override
    public ResponseEntity<Void> productsCreateProductPost(@RequestBody Product product){
        System.out.println("Product created: " + product);
       return ResponseEntity.ok().build();
    }
}
