package com.ivanfrias.company.orders.services;

import com.ivanfrias.companies.model.StateDTO;
import com.ivanfrias.company.common.exceptions.NotFoundException;
import com.ivanfrias.company.orders.dao.entities.StateEntity;
import com.ivanfrias.company.orders.dao.repositories.StateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final ModelMapper modelMapper;

    public List<StateDTO> getOrderStates() {
        List<StateEntity> stateEntities = stateRepository.findAll();
        if(stateEntities.isEmpty()){
            throw new NotFoundException("No hay ningun estado para ordenes en la base de datos");
        }
        return stateEntities.stream()
                .map(stateEntity -> modelMapper.map(stateEntity, StateDTO.class))
                .toList();
    }

    public StateEntity getStateEntityById(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new NotFoundException("State not found: " + stateId));
    }
}
