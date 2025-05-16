package com.ivanfrias.company.products.controllers;

import com.ivanfrias.companies.api.CategoriesApi;
import com.ivanfrias.companies.model.CategoryDTO;
import com.ivanfrias.companies.model.CategoryRequestDTO;
import com.ivanfrias.company.common.exceptions.utils.ControllerUtils;
import com.ivanfrias.company.common.exceptions.utils.UnauthorizedException;
import com.ivanfrias.company.products.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ivanfrias.company.common.exceptions.utils.ControllerUtilsConstants.STRING_NO_PREMISSIONS;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CategoryController extends ControllerUtils implements CategoriesApi {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<CategoryDTO> createCategory(CategoryRequestDTO categoryRequestDTO) {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }

        Long userId = getAllClaims().get("user_id", Long.class);
        return ResponseEntity.ok(categoryService.createCategory(categoryRequestDTO, userId));
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getCategoriesByUserId() {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }

        Long userId = getAllClaims().get("user_id", Long.class);
        return ResponseEntity.ok(categoryService.getCategoriesByUserId(userId));
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(Long categoryId) {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }

        Long userId = getAllClaims().get("user_id", Long.class);
        categoryService.deleteCategoryById(categoryId, userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(Long categoryId) {
        return CategoriesApi.super.getCategoryById(categoryId);
    }
}
