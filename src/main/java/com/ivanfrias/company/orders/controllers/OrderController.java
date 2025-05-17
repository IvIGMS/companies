package com.ivanfrias.company.orders.controllers;

import com.ivanfrias.companies.api.OrdersApi;
import com.ivanfrias.companies.model.OrderDTO;
import com.ivanfrias.companies.model.OrderRequestDTO;
import com.ivanfrias.company.common.exceptions.utils.ControllerUtils;
import com.ivanfrias.company.common.exceptions.utils.UnauthorizedException;
import com.ivanfrias.company.orders.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ivanfrias.company.common.exceptions.utils.ControllerUtilsConstants.STRING_NO_PREMISSIONS;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OrderController extends ControllerUtils implements OrdersApi {
    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderDTO> createOrder(OrderRequestDTO orderRequestDTO) {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }

        Long userId = getAllClaims().get("user_id", Long.class);
        return ResponseEntity.ok(orderService.createOrder(orderRequestDTO, userId));
    }
}
