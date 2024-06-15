package com.kamenskiy.io.productmicroservice.service;

import com.kamenskiy.io.productmicroservice.service.dto.CreateProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductDto productDto) throws ExecutionException, InterruptedException;
}
