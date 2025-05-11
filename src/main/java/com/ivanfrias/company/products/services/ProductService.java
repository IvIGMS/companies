package com.ivanfrias.company.products.services;

import com.ivanfrias.companies.model.ProductDTO;
import com.ivanfrias.company.common.exceptions.NotFoundException;
import com.ivanfrias.company.products.dao.entities.ProductEntity;
import com.ivanfrias.company.products.dao.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductDTO getProductById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));
        return modelMapper.map(productEntity, ProductDTO.class);
    }
}
