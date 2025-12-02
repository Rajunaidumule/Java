package com.td.productCatalog.process;

import com.td.model.Product;
import com.td.productCatalog.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ProductCatalogProcessor {

    @Autowired
    ProductCatalogService service;
    @Autowired
    TaskExecutor executor;

    public void createProduct(Product product) {
       // service.createProduct(product);
        CompletableFuture<List<Product>> future1 = CompletableFuture.supplyAsync(() ->service.getProducts(), executor);
        CompletableFuture future2 = CompletableFuture.runAsync(() ->service.createProduct(product),executor);
        CompletableFuture.allOf(future1,future2).join();
        List<Product> list = future1.join();
        System.out.println("Products after addition: "+list);
    }

    public List<Product> getAllProducts() {

      return service.getProducts();
    }



}
