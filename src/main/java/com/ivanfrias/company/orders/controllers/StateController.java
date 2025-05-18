package com.ivanfrias.company.orders.controllers;

import com.ivanfrias.companies.api.OrderStatesApi;
import com.ivanfrias.companies.model.StateDTO;
import com.ivanfrias.company.common.exceptions.utils.ControllerUtils;
import com.ivanfrias.company.common.exceptions.utils.UnauthorizedException;
import com.ivanfrias.company.orders.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ivanfrias.company.common.exceptions.utils.ControllerUtilsConstants.STRING_NO_PREMISSIONS;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class StateController extends ControllerUtils implements OrderStatesApi {
    private final StateService stateService;

    @Override
    public ResponseEntity<List<StateDTO>> getOrderStates() {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }
        return ResponseEntity.ok(stateService.getOrderStates());
    }
}
