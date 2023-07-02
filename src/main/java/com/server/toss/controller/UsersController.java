package com.server.toss.controller;

import com.server.toss.dto.RegisterDto;
import com.server.toss.repository.UsersRepository;
import com.server.toss.service.UsersService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterDto.Response register(@NotNull RegisterDto.Request registerDto) {
        if (RegisterDto.Request.hasNullDataBeforeSignup(registerDto)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }

        return usersService.insertUser(registerDto);
    }

    @DeleteMapping("/unregister")
    public String unRegister() {
        return null;
    }
}
