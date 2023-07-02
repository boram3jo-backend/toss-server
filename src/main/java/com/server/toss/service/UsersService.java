package com.server.toss.service;

import com.server.toss.domain.Users;
import com.server.toss.dto.RegisterDto;
import com.server.toss.error.exception.DuplicateEmailException;
import com.server.toss.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional
    public RegisterDto.Response insertUser(RegisterDto.Request registerDto) {

        String email = registerDto.getEmail();
        String password = registerDto.getPassword();
        String name = registerDto.getName();
        String birthday = registerDto.getBirthday();

        boolean duplEmailResult = isDuplicatedId(email);
        if (duplEmailResult) {
            throw new DuplicateEmailException("중복된 이메일 입니다.");
        }

        Users newUser = Users.builder()
                .email(email)
                .password(password)
                .name(name)
                .birthday(birthday)
                .build();

        usersRepository.save(newUser);

        return RegisterDto.Response.from(newUser);
    }

    private boolean isDuplicatedId(String email) {
        return usersRepository.countByEmail(email) > 0 ? true : false;
    }
}
