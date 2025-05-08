package com.ivanfrias.company.products.controllers;

import com.ivanfrias.company.products.services.ProductService;
import com.ivanfrias.products.api.ProductsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ProductController implements ProductsApi {
    private final ProductService productService;


}
