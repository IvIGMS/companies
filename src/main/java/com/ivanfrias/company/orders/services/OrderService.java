package com.ivanfrias.company.orders.services;

import com.ivanfrias.companies.model.OrderDTO;
import com.ivanfrias.companies.model.OrderRequestDTO;
import com.ivanfrias.companies.model.ProductDTO;
import com.ivanfrias.company.common.exceptions.NotFoundException;
import com.ivanfrias.company.company.dao.entities.CompanyEntity;
import com.ivanfrias.company.company.services.CompanyService;
import com.ivanfrias.company.orders.dao.dto.OrderStateDTO;
import com.ivanfrias.company.orders.dao.entities.OrderEntity;
import com.ivanfrias.company.orders.dao.entities.OrderStateEntity;
import com.ivanfrias.company.orders.dao.entities.StateEntity;
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
    private final OrderStateService orderStateService;
    private final StateService stateService;
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

    @Transactional
    public OrderDTO createOrder(OrderRequestDTO orderRequestDTO, Long userId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        ProductEntity product = productService.getProductEntityById(orderRequestDTO.getProductId());
        if(!product.getCompany().getId().equals(company.getId())) {
            throw new NotFoundException("Este producto no pertenece a la compañía de este usuario.");
        }
        OrderEntity orderToBeSaved = modelMapper.map(orderRequestDTO, OrderEntity.class);
        orderToBeSaved.setId(null);
        OrderEntity orderSaved = orderRepository.save(orderToBeSaved);

        OrderStateEntity orderStateEntityCreated = orderStateService.createOrderState(
                OrderStateDTO.builder()
                        .current(true)
                        .orderId(orderSaved.getId())
                        .stateId(1L)
                        .build()
        );
        orderSaved.setOrderStates(List.of(orderStateEntityCreated));
        return modelMapper.map(orderSaved, OrderDTO.class);
    }

    public List<OrderDTO> getOrders(Long userId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        List<OrderEntity> orders = orderRepository.getOrdersByCompanyId(company.getId());
        return orders.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, OrderDTO.class))
                .toList();
    }

    @Transactional
    public void changeOrderStatus(Long orderId, Long userId, Long stateId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        OrderEntity order = getOrderEntityById(orderId);
        ProductEntity product = productService.getProductEntityById(order.getProduct().getId());

        if(!product.getCompany().getId().equals(company.getId())) {
            throw new NotFoundException("Este pedido no pertenece a la compañía de este usuario.");
        }
        OrderStateEntity currentOrderState = getCurrentState(order.getOrderStates());

        if(currentOrderState.getState().getId().equals(1L) && stateId.equals(2L)) {
            currentOrderState.setCurrent(false);
            orderStateService.createOrderState(
                    OrderStateDTO.builder()
                            .current(true)
                            .orderId(order.getId())
                            .stateId(2L)
                            .build()
            );
            product.setQuantity(product.getQuantity() + order.getTotalAmount());
        } else if(currentOrderState.getState().getId().equals(2L) && stateId.equals(3L)) {
            currentOrderState.setCurrent(false);
            orderStateService.createOrderState(
                    OrderStateDTO.builder()
                            .current(true)
                            .orderId(order.getId())
                            .stateId(3L)
                            .build()
            );
            product.setQuantity(product.getQuantity() - order.getTotalAmount());
        } else if(currentOrderState.getState().getId().equals(1L) && stateId.equals(3L)) {
            currentOrderState.setCurrent(false);
            orderStateService.createOrderState(
                    OrderStateDTO.builder()
                            .current(true)
                            .orderId(order.getId())
                            .stateId(3L)
                            .build()
            );
        }
    }

    private OrderStateEntity getCurrentState(List<OrderStateEntity> states) {
        List<OrderStateEntity> orderStateEntityList = states.stream()
                .filter(OrderStateEntity::isCurrent)
                .toList();

        if (orderStateEntityList.size() != 1) {
            throw new IllegalStateException("Debe haber exactamente un estado actual por pedido");
        }

        return orderStateEntityList.get(0);
    }
}
