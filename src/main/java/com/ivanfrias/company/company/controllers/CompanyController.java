package com.ivanfrias.company.company.controllers;

import com.ivanfrias.companies.model.CompanyDTO;
import com.ivanfrias.company.common.exceptions.utils.ControllerUtils;
import com.ivanfrias.company.common.exceptions.utils.UnauthorizedException;
import com.ivanfrias.company.company.services.CompanyService;
import com.ivanfrias.companies.api.CompaniesApi;
import com.ivanfrias.companies.model.CompanyRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ivanfrias.company.common.exceptions.utils.ControllerUtilsConstants.STRING_NO_PREMISSIONS;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CompanyController extends ControllerUtils implements CompaniesApi {
    private final CompanyService companyService;

    @Override
    public ResponseEntity<com.ivanfrias.companies.model.CompanyDTO> createCompany(CompanyRequestDTO companyRequestDTO) {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }

        Long userId = getAllClaims().get("user_id", Long.class);
        return ResponseEntity.ok(companyService.createCompany(companyRequestDTO, userId));
    }

    @Override
    public ResponseEntity<CompanyDTO> getSelfCompany() {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }
        Long userId = getAllClaims().get("user_id", Long.class);
        return ResponseEntity.ok(companyService.getCompanyByUserId(userId));
    }
}
