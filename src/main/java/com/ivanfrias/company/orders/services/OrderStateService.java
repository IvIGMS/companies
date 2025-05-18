package com.ivanfrias.company.orders.services;

import com.ivanfrias.company.orders.dao.dto.OrderStateDTO;
import com.ivanfrias.company.orders.dao.entities.OrderEntity;
import com.ivanfrias.company.orders.dao.entities.OrderStateEntity;
import com.ivanfrias.company.orders.dao.entities.StateEntity;
import com.ivanfrias.company.orders.dao.repositories.OrderStateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStateService {
    private final OrderStateRepository orderStateRepository;
    private final ModelMapper modelMapper;

    public OrderStateEntity createOrderState(OrderStateDTO orderStateDTO) {
        OrderStateEntity orderStateEntityToBeSaved = OrderStateEntity.builder()
                .state(StateEntity.builder().id(orderStateDTO.getStateId()).build())
                .order(OrderEntity.builder().id(orderStateDTO.getOrderId()).build())
                .current(orderStateDTO.isCurrent())
                .build();
        return orderStateRepository.save(orderStateEntityToBeSaved);
    }
}
