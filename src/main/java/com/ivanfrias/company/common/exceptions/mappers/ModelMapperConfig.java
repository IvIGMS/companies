package com.ivanfrias.company.common.exceptions.mappers;

import com.ivanfrias.companies.model.CompanyDTO;
import com.ivanfrias.companies.model.UserDTO;
import com.ivanfrias.company.company.dao.entities.CompanyEntity;
import com.ivanfrias.company.security.dao.models.entities.UserEntity;
import org.apache.catalina.User;
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
    private void configureCategoryMapping(ModelMapper mapper) {}
    private void configureUserMapping(ModelMapper mapper) {}
}