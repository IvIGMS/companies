package com.ivanfrias.company.products.controllers;

import com.ivanfrias.companies.api.ProductsApi;
import com.ivanfrias.companies.model.PagedResponseProductDTO;
import com.ivanfrias.companies.model.ProductDTO;
import com.ivanfrias.companies.model.ProductRequestDTO;
import com.ivanfrias.company.common.exceptions.utils.ControllerUtils;
import com.ivanfrias.company.common.exceptions.utils.PaginationUtils;
import com.ivanfrias.company.common.exceptions.utils.UnauthorizedException;
import com.ivanfrias.company.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ivanfrias.company.common.exceptions.utils.ControllerUtilsConstants.STRING_NO_PREMISSIONS;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ProductController extends ControllerUtils implements ProductsApi {
    private final ProductService productService;

    @Override
    public ResponseEntity<ProductDTO> getProductById(Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @Override
    public ResponseEntity<ProductDTO> createProduct(ProductRequestDTO productRequestDTO) {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }

        Long userId = getAllClaims().get("user_id", Long.class);
        return ResponseEntity.ok(productService.createProduct(productRequestDTO, userId));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getProductsFilter(String productName, String categoryName, Double minPrice, Double maxPrice) {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }

        Long userId = getAllClaims().get("user_id", Long.class);
        return ResponseEntity.ok(productService.getProductsFilter(productName, categoryName, minPrice, maxPrice, userId));
    }

    @Override
    public ResponseEntity<Void> deleteProductById(Long productId) {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }

        Long userId = getAllClaims().get("user_id", Long.class);
        productService.deleteProductById(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PagedResponseProductDTO> getPagedProductsFilter(
            String productName,
            String categoryName,
            Double minPrice,
            Double maxPrice,
            Integer pageNumberQueryParam,
            Integer pageSizeQueryParam,
            String sortByQueryParam
    ) {
        Pageable pageable = PaginationUtils.createPageable(pageNumberQueryParam, pageSizeQueryParam, sortByQueryParam);
        Page<ProductDTO> products = productService.getPagedProductsFilter(productName, categoryName, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(PaginationUtils.fromPage(products));
    }
}
