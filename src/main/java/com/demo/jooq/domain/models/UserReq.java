package com.demo.jooq.domain.models;

import lombok.Getter;
import lombok.Setter;

/**
 * User 요청 DTO
 * <p>
 * 사용자 생성, 수정 등의 요청에서 사용되는 데이터 전송 객체입니다.
 * 이 클래스는 사용자 정보를 담고 있으며 요청 본문과 매핑됩니다.
 */
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