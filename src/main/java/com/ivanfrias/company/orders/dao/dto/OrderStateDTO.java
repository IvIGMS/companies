package com.ivanfrias.company.orders.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStateDTO {
    private Long orderId;
    private Long stateId;
    private String comment;
    private boolean current;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
