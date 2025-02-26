package com.demo.jooq.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
