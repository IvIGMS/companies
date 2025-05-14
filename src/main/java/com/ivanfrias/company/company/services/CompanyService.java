package com.ivanfrias.company.company.services;

import com.ivanfrias.companies.model.CompanyDTO;
import com.ivanfrias.companies.model.CompanyRequestDTO;
import com.ivanfrias.company.common.exceptions.DataBaseErrorException;
import com.ivanfrias.company.common.exceptions.ConflictException;
import com.ivanfrias.company.common.exceptions.NotFoundException;
import com.ivanfrias.company.company.dao.entities.CompanyEntity;
import com.ivanfrias.company.company.dao.repositories.CompanyRepository;
import com.ivanfrias.company.security.dao.models.entities.UserEntity;
import com.ivanfrias.company.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CompanyDTO createCompany(CompanyRequestDTO companyRequestDTO, Long userId) {
        CompanyEntity companyEntityToBeSaved = modelMapper.map(companyRequestDTO, CompanyEntity.class);
        UserEntity user = userService.getUserEntityById(userId);
        if(!user.getIsActive()){
            throw new ConflictException("El correo electronico del usuario no ha sido verificado");
        }
        companyEntityToBeSaved.setUser(user);
        CompanyEntity companyEntitySaved;
        try{
            companyEntitySaved = companyRepository.save(companyEntityToBeSaved);
        } catch (Exception e){
            throw new DataBaseErrorException("Error al introducir la company en la bbdd");
        }
        return modelMapper.map(companyEntitySaved, CompanyDTO.class);
    }

    public CompanyDTO getCompanyByUserId(Long userId) {
        CompanyEntity company = companyRepository
                .findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(
                        "No se ha encontrado la company con el id indicado")
                );
        return modelMapper.map(company, CompanyDTO.class);
    }

    public CompanyEntity getCompanyEntityByUserId(Long userId) {
        return companyRepository
                .findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(
                        "No se ha encontrado la company con el id indicado")
                );
    }
}
