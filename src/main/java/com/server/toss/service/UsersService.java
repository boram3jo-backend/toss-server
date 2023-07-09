package com.server.toss.service;

import com.server.toss.domain.Users;
import com.server.toss.dto.LoginDto;
import com.server.toss.dto.RefreshDto;
import com.server.toss.dto.RegisterDto;
import com.server.toss.dto.UsersDto;
import com.server.toss.error.exception.*;
import com.server.toss.jwt.JwtTokenProvider;
import com.server.toss.repository.RedisService;
import com.server.toss.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    public UsersDto registerUser(String email, String password, String name, String birthday) {

        boolean duplEmailResult = isDuplicatedEmail(email);

        if (duplEmailResult) {
            throw new DuplicateEmailException("중복된 이메일 입니다.");
        }

        return UsersDto.fromEntity(
                usersRepository.save(Users.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .name(name)
                    .birthday(birthday)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build())
        );
    }

    private boolean isDuplicatedEmail(String email) {
        return usersRepository.countByEmail(email) > 0 ? true : false;
    }

    @Transactional
    public LoginDto.Response loginUser(String email, String password) {
        Users findUser = usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(email, findUser.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(email, findUser.getRoles());

        redisService.setValues(refreshToken, email);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes())
                .getResponse();

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        return LoginDto.Response.of(accessToken, refreshToken);
    }

    @Transactional
    public RefreshDto.Response refreshToken(String refreshToken) {

        if (!jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.existsRefreshToken(refreshToken)) {
            throw new JwtExpireException("토큰 정보가 만료되었습니다. 다시 로그인 해주세요.");
        }

        String email = jwtTokenProvider.getUserEmail(refreshToken);

        if (!redisService.getValues(refreshToken).equals(email)) {
            throw new JwtValidFailException("토큰 검증에 실패하였습니다.");
        }

        Users findUser = usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        String accessToken = jwtTokenProvider.createAccessToken(email, findUser.getRoles());
        return RefreshDto.Response.from(accessToken);
    }

    @Transactional
    public void unRegisterUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Users findUser = usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        usersRepository.delete(findUser);
    }
}
