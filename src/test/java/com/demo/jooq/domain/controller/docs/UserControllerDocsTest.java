package com.demo.jooq.domain.controller.docs;

import com.demo.jooq.domain.controller.UserController;
import com.demo.jooq.domain.models.UserReq;
import com.demo.jooq.domain.models.UserRes;
import com.demo.jooq.domain.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.replacePattern;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class UserControllerDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(modifyUris()
                                .scheme("https")
                                .host("example.com")
                                .removePort()
                        )
                )
                .build();
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserRes> users = Arrays.asList(
                createUserRes(1L, "user1", "user1@test.com"),
                createUserRes(2L, "user2", "user2@test.com")
        );

        given(userService.getAll()).willReturn(users);

        // when & then
        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("users-list",
                        responseFields(
                                fieldWithPath("[].id").description("사용자 ID"),
                                fieldWithPath("[].userName").description("사용자 이름"),
                                fieldWithPath("[].loginId").description("로그인 ID"),
                                fieldWithPath("[].email").description("이메일"),
                                fieldWithPath("[].phoneNumber").description("전화번호").optional(),
                                fieldWithPath("[].mobilePhoneNumber").description("휴대전화 번호").optional(),
                                fieldWithPath("[].picture").description("프로필 사진 URL").optional(),
                                fieldWithPath("[].createdAt").description("생성일시"),
                                fieldWithPath("[].lastUpdatedAt").description("최근수정일시")
                        )
                ));

    }

    @Test
    void createUser() throws Exception {
        UserReq newUser = createUserReq(null, "newUser", "new@test.com");
        UserRes savedUser = createUserRes(1L, "newUser", "new@test.com");

        given(userService.createUser(any(UserReq.class))).willReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("id").description("ID"),
                                fieldWithPath("userName").description("사용자 이름"),
                                fieldWithPath("loginId").description("로그인 ID"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("전화번호").optional(),
                                fieldWithPath("mobilePhoneNumber").description("휴대전화 번호").optional(),
                                fieldWithPath("picture").description("프로필 사진 URL").optional()
                        ),
                        responseFields(
                                fieldWithPath("id").description("사용자 ID"),
                                fieldWithPath("userName").description("사용자 이름"),
                                fieldWithPath("loginId").description("로그인 ID"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("전화번호").optional(),
                                fieldWithPath("mobilePhoneNumber").description("휴대전화 번호").optional(),
                                fieldWithPath("picture").description("프로필 사진 URL").optional(),
                                fieldWithPath("createdAt").description("생성일시"),
                                fieldWithPath("lastUpdatedAt").description("최근수정일시")
                        )
                ));
    }

    @Test
    void getUserById() throws Exception {
        UserRes user = createUserRes(1L, "user1", "user1@test.com");

        given(userService.getUserById(1L)).willReturn(user);

        mockMvc.perform(get("/api/users/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-get",
                        pathParameters(
                                parameterWithName("id").description("조회할 사용자 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("사용자 ID"),
                                fieldWithPath("userName").description("사용자 이름"),
                                fieldWithPath("loginId").description("로그인 ID"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("전화번호").optional(),
                                fieldWithPath("mobilePhoneNumber").description("휴대전화 번호").optional(),
                                fieldWithPath("picture").description("프로필 사진 URL").optional(),
                                fieldWithPath("createdAt").description("생성일시"),
                                fieldWithPath("lastUpdatedAt").description("최근수정일시")
                        )
                ));
    }

    @Test
    void getUserById_NotFound() throws Exception {
        given(userService.getUserById(999L)).willThrow(new IllegalArgumentException("사용자를 찾을 수 없습니다: ID = " + 999L));

        mockMvc.perform(get("/api/users/{id}", 999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(document("user-get-error",
                        pathParameters(
                                parameterWithName("id").description("존재하지 않는 사용자 ID")
                        )
                ));
    }

    @Test
    void updateUser() throws Exception {
        UserReq userReq = createUserReq(1L, "updatedUser", "updated@test.com");
        UserRes userRes = createUserRes(1L, "updatedUser", "updated@test.com");

        given(userService.updateUser(eq(1L), any(UserReq.class)))
                .willReturn(Optional.of(userRes));

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userReq)))
                .andExpect(status().isOk())
                .andDo(document("user-update",
                        pathParameters(
                                parameterWithName("id").description("수정할 사용자 ID")
                        ),
                        requestFields(
                                fieldWithPath("id").description("사용자 ID"),
                                fieldWithPath("userName").description("사용자 이름"),
                                fieldWithPath("loginId").description("로그인 ID"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("전화번호").optional(),
                                fieldWithPath("mobilePhoneNumber").description("휴대전화 번호").optional(),
                                fieldWithPath("picture").description("프로필 사진 URL").optional()
                        )
                ));
    }

    @Test
    void deleteUser() throws Exception {
        given(userService.deleteUser(1L)).willReturn(true);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent())
                .andDo(document("user-delete",
                        pathParameters(
                                parameterWithName("id").description("삭제할 사용자 ID")
                        )
                ));
    }

    private UserReq createUserReq(Long id, String name, String email) {
        UserReq user = new UserReq();
        user.setId(id);
        user.setUserName(name);
        user.setLoginId(name);
        user.setEmail(email);
        return user;
    }

    private UserRes createUserRes(Long id, String name, String email) {
        UserRes user = new UserRes();
        user.setId(id);
        user.setUserName(name);
        user.setLoginId(name);
        user.setEmail(email);
        user.setCreatedAt(LocalDateTime.now());
        user.setLastUpdatedAt(LocalDateTime.now());
        return user;
    }
}