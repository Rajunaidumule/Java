package com.td.productCatalog.process;

import com.td.model.Product;
import com.td.productCatalog.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductCatalogProcessor {

    @Autowired
    ProductCatalogService service;
    @Autowired
    TaskExecutor executor;

    public void createProduct(Product product) {
        service.createProduct(product);
    }

    public List<Product> getAllProducts() {

        executor.execute(() -> method1());
        executor.execute(() -> method2());
        executor.execute(() -> method3());
        executor.execute(() -> method4());
        executor.execute(() -> method5());
        executor.execute(() -> method6());
        executor.execute(() -> method7());
      return service.getProducts();
    }

    public void method1(){
        System.out.println("inside method1");
    }

    public void method2(){
        System.out.println("inside method2");
    }
    public void method3(){
        System.out.println("inside method3");
    }
    public void method4(){
        System.out.println("inside method4");
    }
    public void method5(){
        System.out.println("inside method5");
    }
    public void method6(){
        System.out.println("inside method6");
    }
    public void method7(){
        System.out.println("inside method7");
    }
}
