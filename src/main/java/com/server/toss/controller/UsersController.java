package com.server.toss.controller;

import com.server.toss.dto.LoginDto;
import com.server.toss.dto.RefreshDto;
import com.server.toss.dto.RegisterDto;
import com.server.toss.error.exception.JwtExpireException;
import com.server.toss.error.exception.RequestNullDataException;
import com.server.toss.jwt.JwtTokenProvider;
import com.server.toss.service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterDto.Response register(@Valid RegisterDto.Request registerDto) {
        if (RegisterDto.Request.hasNullDataBeforeRegister(registerDto)) {
            throw new RequestNullDataException("회원가입 시 필수 데이터를 모두 입력해야 합니다.");
        }

        return RegisterDto.Response.from(
                usersService.registerUser(
                    registerDto.getEmail(),
                    registerDto.getPassword(),
                    registerDto.getName(),
                    registerDto.getBirthday()
                )
        );
    }

    @DeleteMapping("/unregister")
    public ResponseEntity<?> unRegister() {

        usersService.unRegisterUser();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public LoginDto.Response login(@NotNull LoginDto.Request loginDto) {
        if (LoginDto.Request.hasNullDataBeforeLogin(loginDto)) {
            throw new RequestNullDataException("로그인 시 필요 데이터를 모두 입력해야 합니다.");
        }

        return usersService.loginUser(
                loginDto.getEmail(),
                loginDto.getPassword()
        );
    }

    @PostMapping("/refresh")
    public RefreshDto.Response refresh(@NotNull RefreshDto.Request refreshDto) {
        if (RefreshDto.Request.hasNullDataBeforeRefresh(refreshDto)) {
            throw new RequestNullDataException("토큰 갱신 시 필요 데이터를 모두 입력해야 합니다.");
        }

        return usersService.refreshToken(refreshDto.getRefreshToken());
    }
}
