package com.server.toss.dto;

import com.server.toss.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UsersDto {
    private Long id;
    private String email;
    private String name;

    public static UsersDto fromEntity(Users user) {
        return UsersDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
