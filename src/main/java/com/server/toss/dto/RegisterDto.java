package com.server.toss.dto;

import com.server.toss.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class RegisterDto {
    private RegisterDto() {}

    @Getter
    public static class Request {

        @NonNull
        private String email;
        @NonNull
        private String password;
        @NonNull
        private String name;
        @NonNull
        private String birthday;
        public static boolean hasNullDataBeforeSignup(Request registerDto) {
            return  registerDto.getEmail() == null
                    || registerDto.getPassword() == null
                    || registerDto.getName() == null
                    || registerDto.getBirthday() == null;
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String email;
        private String name;

        public static Response from(Users user) {
            return Response.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();
        }
    }
}