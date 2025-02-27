package com.demo.jooq.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 사용자 응답 DTO
 * <p>이 DTO는 사용자 정보를 반환하는 데 사용됩니다.
 * 사용자 세부 정보를 포함합니다.
 */
@Getter
@Setter
public class UserRes {
    private Long id;
    private String userName;
    private String loginId;
    private String email;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String picture;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
