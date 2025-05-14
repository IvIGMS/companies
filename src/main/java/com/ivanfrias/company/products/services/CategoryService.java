package com.ivanfrias.company.products.services;

import com.ivanfrias.companies.model.CategoryDTO;
import com.ivanfrias.companies.model.CategoryRequestDTO;
import com.ivanfrias.company.common.exceptions.DataBaseErrorException;
import com.ivanfrias.company.common.exceptions.NotFoundException;
import com.ivanfrias.company.company.dao.entities.CompanyEntity;
import com.ivanfrias.company.company.services.CompanyService;
import com.ivanfrias.company.products.dao.entities.CategoryEntity;
import com.ivanfrias.company.products.dao.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final CompanyService companyService;

    public CategoryDTO createCategory(CategoryRequestDTO categoryRequestDTO, Long userId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        CategoryEntity categoryEntityToSave = modelMapper.map(categoryRequestDTO, CategoryEntity.class);
        categoryEntityToSave.setCompany(company);

        CategoryEntity categoryEntitySaved;
        try{
            categoryEntitySaved = categoryRepository.save(categoryEntityToSave);
        } catch (Exception e){
            throw new DataBaseErrorException("Error al introducir la categoría en la bbdd");
        }
        return modelMapper.map(categoryEntitySaved, CategoryDTO.class);
    }

    public List<CategoryDTO> getCategoriesByUserId(Long userId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        List<CategoryEntity> categoryEntities = categoryRepository.findByCompanyId(company.getId());
        if(categoryEntities.isEmpty()){
            throw new NotFoundException("No hay ninguna categoría asociada a este user");
        }
        return categoryEntities.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
                .toList();
    }

    public CategoryEntity getCategoriesById(Long userId, Long categoryId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        return categoryRepository.findByCompanyIdAndId(company.getId(), categoryId);
    }
}
