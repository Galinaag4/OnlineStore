package com.example.onlinestore.controller;

import com.example.onlinestore.dto.Role;
import com.example.onlinestore.service.ImageService;
import com.example.onlinestore.service.UserService;
import com.example.onlinestore.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.onlinestore.dto.LoginReq;
import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.service.AuthService;

import static com.example.onlinestore.dto.Role.USER;

/**
 * Класс - контроллер для авторизации и регистрации пользователя
 *
 * @see AuthService
 */

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;


    @Operation (
            summary = "Авторизация пользователя",
            description = "Авторизация пользователя из тела запроса",
            tags = "Авторизация"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь авторизирован",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = LoginReq.class))
                            )
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        if (authServiceImpl.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрация пользователя из тела запроса",
            tags = "Регистрация"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = RegisterReq.class))
                            )})})

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        Role role = req.getRole() == null ? USER : req.getRole();
        if (authServiceImpl.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
