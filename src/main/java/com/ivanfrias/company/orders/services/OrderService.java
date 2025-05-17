package com.ivanfrias.company.orders.services;

import com.ivanfrias.companies.model.OrderDTO;
import com.ivanfrias.companies.model.OrderRequestDTO;
import com.ivanfrias.company.common.exceptions.NotFoundException;
import com.ivanfrias.company.company.dao.entities.CompanyEntity;
import com.ivanfrias.company.company.services.CompanyService;
import com.ivanfrias.company.orders.dao.entities.OrderEntity;
import com.ivanfrias.company.orders.dao.repositories.OrderRepository;
import com.ivanfrias.company.products.dao.entities.ProductEntity;
import com.ivanfrias.company.products.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CompanyService companyService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public OrderDTO createOrder(OrderRequestDTO orderRequestDTO, Long userId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        ProductEntity product = productService.getProductEntityById(orderRequestDTO.getProductId());
        if(!product.getCompany().getId().equals(company.getId())) {
            throw new NotFoundException("Este producto no pertenece a la compañía de este usuario.");
        }
        OrderEntity orderToBeSaved = modelMapper.map(orderRequestDTO, OrderEntity.class);
        orderToBeSaved.setId(null);
        OrderEntity orderSaved = orderRepository.save(orderToBeSaved);

        return modelMapper.map(orderSaved, OrderDTO.class);
    }
}
