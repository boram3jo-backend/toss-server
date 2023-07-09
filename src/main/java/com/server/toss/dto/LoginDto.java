package com.server.toss.dto;

import lombok.*;

public class LoginDto {
    private LoginDto() {}

    @Getter
    @Setter
    public static class Request {
        @NonNull
        private String email;
        @NonNull
        private String password;

        public static boolean hasNullDataBeforeLogin(LoginDto.Request loginDto) {
            return loginDto.getEmail() == null
                    || loginDto.getPassword() == null;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String accessToken;
        private String refreshToken;

        public static Response of(String accessToken, String refreshToken) {
            return Response.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }
}
