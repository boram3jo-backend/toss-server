package com.server.toss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class RefreshDto {
    private RefreshDto() {}

    @Getter
    @Setter
    public static class Request {
        private String refreshToken;

        public static boolean hasNullDataBeforeRefresh(Request refreshDto) {
            return refreshDto.getRefreshToken() == null;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String accessToken;

        public static Response from(String accessToken) {
            return Response.builder()
                    .accessToken(accessToken)
                    .build();
        }
    }
}
