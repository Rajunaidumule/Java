package com.td.productCatalog.service;

import com.td.model.Product;
import com.td.productCatalog.builder.WebClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;

@Service
public class ProductCatalogService {

    @Qualifier("customWebClientBuilder")
    @Autowired
    WebClientBuilder webClientBuilder;


    public void createProduct(Product product) {
        String output =webClientBuilder.invokeWebClientCall("/saveProduct", product,String.class,null, HttpMethod.POST);
        System.out.println("called successfully"+output);
    }
}
