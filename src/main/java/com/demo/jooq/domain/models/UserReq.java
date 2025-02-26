package com.demo.jooq.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReq {
    private Long id;
    private String userName;
    private String loginId;
    private String email;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String picture;
}
