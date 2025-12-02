package com.td.productCatalog.service;

import com.td.model.Product;
import com.td.productCatalog.builder.WebClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.print.DocFlavor;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductCatalogService {

    @Qualifier("customWebClientBuilder")
    @Autowired
    WebClientBuilder webClientBuilder;


    public void createProduct(Product product) {
        String output =webClientBuilder.invokeWebClientCall("/saveProduct", product,String.class,null, HttpMethod.POST);
        System.out.println("called successfully"+output);
    }

    public List<Product> getProducts() {
        System.out.println("calling service to get products");
        return webClientBuilder.invokeWebClientCallforList("/getProducts",null,null,HttpMethod.GET);
    }
}
