package com.ivanfrias.company.security.services;

import com.ivanfrias.companies.model.UserDTO;
import com.ivanfrias.company.common.exceptions.NotFoundException;
import com.ivanfrias.company.security.dao.models.entities.UserEntity;
import com.ivanfrias.company.security.dao.models.enums.RoleEnum;
import com.ivanfrias.company.security.dao.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Value("${activate.user.mocked}")
    private boolean isUserMocked;

    public UserEntity getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));
    }

    public UserDTO getUserDTOById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public void activateUser(Long userId) {
        if(isUserMocked){
            UserEntity user = getUserEntityById(userId);
            user.setIsActive(true);
        } else {
            // todo: implemetar esto con el correo.
        }
    }

    public void deleteUserById(Long userId) {
        getUserEntityById(userId);
        userRepository.deleteById(userId);
    }

    public List<UserDTO> getUsers() {
        List<UserEntity> entityUsers = userRepository.findAllByRole(RoleEnum.USER);
        if(entityUsers.isEmpty()){
            throw new NotFoundException("No hay ningún usuario con role USER registrado");
        }
        return entityUsers.stream()
                .map(ue -> modelMapper.map(ue, UserDTO.class))
                .toList();
    }
}
