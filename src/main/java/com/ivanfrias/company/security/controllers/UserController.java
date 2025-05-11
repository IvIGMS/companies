package com.ivanfrias.company.security.controllers;

import com.ivanfrias.companies.api.UsersApi;
import com.ivanfrias.companies.model.UserDTO;
import com.ivanfrias.company.common.exceptions.utils.ControllerUtils;
import com.ivanfrias.company.common.exceptions.utils.UnauthorizedException;
import com.ivanfrias.company.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ivanfrias.company.common.exceptions.utils.ControllerUtilsConstants.STRING_NO_PREMISSIONS;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController extends ControllerUtils implements UsersApi {
    private final UserService userService;

    @Override
    public ResponseEntity<Void> activateUser() {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }
        Long userId = getAllClaims().get("user_id", Long.class);

        userService.activateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDTO> getSelfUser() {
        if(!checkIsUser()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }
        Long userId = getAllClaims().get("user_id", Long.class);
        return ResponseEntity.ok(userService.getUserDTOById(userId));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        if(!checkIsAdmin()){
            throw new UnauthorizedException(STRING_NO_PREMISSIONS);
        }
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
