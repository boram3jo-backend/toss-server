package com.server.toss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.toss.WithAuthUser;
import com.server.toss.config.SecurityConfig;
import com.server.toss.dto.LoginDto;
import com.server.toss.dto.RefreshDto;
import com.server.toss.dto.RegisterDto;
import com.server.toss.dto.UsersDto;
import com.server.toss.jwt.JwtTokenProvider;
import com.server.toss.service.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UsersControllerTest {
    @MockBean
    private UsersService usersService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("계정 생성")
    void registerUser() throws Exception {
        // given
        given(usersService.registerUser(anyString(), anyString(), anyString(), anyString()))
                .willReturn(UsersDto.builder()
                        .id(1L)
                        .name("이름")
                        .email("이메일")
                        .build());

        // when

        // then
        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test@test.com")
                .param("password","123456789a")
                .param("name","nickname")
                .param("birthday","20000000"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("이름"))
                .andExpect(jsonPath("$.email").value("이메일"))
                .andDo(print());
    }

    @Test
    @DisplayName("계정 삭제")
    @WithMockUser(username = "admin", roles = {"USER"})
    void unRegisterUser() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(delete("/users/unregister"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("계정 로그인")
    @WithMockUser(username = "admin", roles = {"USER"})
    void loginUser() throws Exception {
        // given
        given(usersService.loginUser(anyString(), anyString()))
                .willReturn(LoginDto.Response.builder()
                        .accessToken("1234567890a")
                        .refreshToken("9876543210a")
                        .build());

        // when

        // then
        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test@test.com")
                .param("password","123456789a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("1234567890a"))
                .andExpect(jsonPath("$.refreshToken").value("9876543210a"))
                .andDo(print());
    }

    @Test
    @DisplayName("토큰 갱신")
    void refreshToken() throws Exception {
        // given
        given(usersService.refreshToken(anyString()))
                .willReturn(RefreshDto.Response.builder()
                        .accessToken("1234567890a")
                        .build());

        // when

        // then
        mockMvc.perform(post("/users/refresh")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("refreshToken", "1234567890a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("1234567890a"))
                .andDo(print());
    }
}