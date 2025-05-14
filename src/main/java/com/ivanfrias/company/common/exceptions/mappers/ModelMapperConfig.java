package com.ivanfrias.company.common.exceptions.mappers;

import com.ivanfrias.companies.model.CategoryDTO;
import com.ivanfrias.companies.model.CompanyDTO;
import com.ivanfrias.company.company.dao.entities.CompanyEntity;
import com.ivanfrias.company.products.dao.entities.CategoryEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Configuration
public class ModelMapperConfig {

    private final Converter<ZonedDateTime, OffsetDateTime> zonedToOffset = ctx -> {
        ZonedDateTime source = ctx.getSource();
        return (source != null) ? source.toOffsetDateTime() : null;
    };

    private final Converter<OffsetDateTime, ZonedDateTime> offsetToZoned = ctx -> {
        OffsetDateTime source = ctx.getSource();
        return (source != null) ? source.toZonedDateTime() : null;
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        configureCompanyMapping(mapper);
        configureProductMapping(mapper);
        configureCategoryMapping(mapper);
        configureUserMapping(mapper);

        return mapper;
    }

    private void configureCompanyMapping(ModelMapper mapper) {
        mapper.typeMap(CompanyEntity.class, CompanyDTO.class).addMappings(m -> {
            m.using(zonedToOffset).map(CompanyEntity::getCreatedAt, CompanyDTO::setCreatedAt);
            m.using(zonedToOffset).map(CompanyEntity::getUpdatedAt, CompanyDTO::setUpdatedAt);
        });

        mapper.typeMap(CompanyDTO.class, CompanyEntity.class).addMappings(m -> {
            m.using(offsetToZoned).map(CompanyDTO::getCreatedAt, CompanyEntity::setCreatedAt);
            m.using(offsetToZoned).map(CompanyDTO::getUpdatedAt, CompanyEntity::setUpdatedAt);
        });
    }

    private void configureProductMapping(ModelMapper mapper) {}
    private void configureCategoryMapping(ModelMapper mapper) {
        mapper.typeMap(CategoryEntity.class, CategoryDTO.class).addMappings(m -> {
            m.using(zonedToOffset).map(CategoryEntity::getCreatedAt, CategoryDTO::setCreatedAt);
            m.using(zonedToOffset).map(CategoryEntity::getUpdatedAt, CategoryDTO::setUpdatedAt);
        });

        mapper.typeMap(CategoryDTO.class, CategoryEntity.class).addMappings(m -> {
            m.using(offsetToZoned).map(CategoryDTO::getCreatedAt, CategoryEntity::setCreatedAt);
            m.using(offsetToZoned).map(CategoryDTO::getUpdatedAt, CategoryEntity::setUpdatedAt);
        });
    }
    private void configureUserMapping(ModelMapper mapper) {}
}