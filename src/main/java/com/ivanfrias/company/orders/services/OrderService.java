package com.ivanfrias.company.orders.services;

import com.ivanfrias.companies.model.OrderDTO;
import com.ivanfrias.companies.model.OrderRequestDTO;
import com.ivanfrias.companies.model.ProductDTO;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CompanyService companyService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public OrderDTO getOrderById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    public OrderEntity getOrderEntityById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
    }

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

    public List<OrderDTO> getOrders(Long userId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        List<OrderEntity> orders = orderRepository.getOrdersByCompanyId(company.getId());
        return orders.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, OrderDTO.class))
                .toList();
    }

    public void isDelivered(Long orderId, Long userId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        OrderEntity order = getOrderEntityById(orderId);
        ProductEntity product = productService.getProductEntityById(order.getProduct().getId());
        if(!product.getCompany().getId().equals(company.getId())) {
            throw new NotFoundException("Este producto no pertenece a la compañía de este usuario.");
        }
        order.setDelivered(true);
        orderRepository.save(order);
    }
}
