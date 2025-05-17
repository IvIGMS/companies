package com.ivanfrias.company.products.services;

 import com.ivanfrias.companies.model.ProductDTO;
import com.ivanfrias.companies.model.ProductRequestDTO;
import com.ivanfrias.company.common.exceptions.DataBaseErrorException;
import com.ivanfrias.company.common.exceptions.NotFoundException;
 import com.ivanfrias.company.company.dao.entities.CompanyEntity;
 import com.ivanfrias.company.company.services.CompanyService;
 import com.ivanfrias.company.products.dao.entities.ProductEntity;
import com.ivanfrias.company.products.dao.repositories.ProductRepository;
 import com.ivanfrias.company.security.dao.models.entities.UserEntity;
 import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.stereotype.Service;
 import org.springframework.util.CollectionUtils;

 import java.util.List;
 import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    private final CategoryService categoryService;

    public ProductDTO getProductById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));
        return modelMapper.map(productEntity, ProductDTO.class);
    }

    public ProductEntity getProductEntityById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));
    }

    public ProductDTO createProduct(ProductRequestDTO productRequestDTO, Long userId) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productRequestDTO.getProductName());
        productEntity.setPrice(productRequestDTO.getPrice());
        productEntity.setQuantity(0);
        productEntity.setCompany(companyService.getCompanyEntityByUserId(userId));
        productEntity.setCategory(categoryService.getCategoriesById(userId, productRequestDTO.getCategoryId()));

        if(Objects.isNull(productEntity.getCategory())) {
            throw new NotFoundException("No existe la categoría introducida");
        }

        ProductEntity productSaved;
        try{
            productSaved = productRepository.save(productEntity);
        } catch (Exception e){
            throw new DataBaseErrorException("Error al introducir el producto en la bbdd");
        }
        return modelMapper.map(productSaved, ProductDTO.class);
    }

    public List<ProductDTO> getProductsFilter(String productName, String categoryName, Double minPrice, Double maxPrice, Long userId) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        List<ProductEntity> productEntities = productRepository.getProductsFilter(productName, categoryName, minPrice, maxPrice, company.getId());
        return productEntities.stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .toList();
    }

    public void deleteProductById(Long userId, Long productId) {
        ProductEntity productEntity = getProductEntityById(productId);
        if(!productEntity.getCompany().getUser().getId().equals(userId)) {
            throw new NotFoundException("Este product id no pertence al usuario");
        }
        productRepository.deleteById(productId);
    }

    public Page<ProductDTO> getPagedProductsFilter(String productName, String categoryName, Long userId, Double minPrice, Double maxPrice, Pageable pageable) {
        CompanyEntity company = companyService.getCompanyEntityByUserId(userId);
        Page<ProductEntity> productEntitiesPaged = (Page<ProductEntity>) productRepository.getPagedProductsFilter(productName, categoryName, company.getId(), minPrice, maxPrice, pageable);

        if(CollectionUtils.isEmpty(productEntitiesPaged.getContent())){
            throw new NotFoundException("No hay ningun producto registrado en la aplicación");
        }
        return productEntitiesPaged.map(productEntity -> modelMapper.map(productEntity, ProductDTO.class));
    }
}
