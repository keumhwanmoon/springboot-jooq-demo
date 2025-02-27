package com.demo.jooq.domain.repositories;

import com.demo.jooq.domain.models.UserReq;
import com.demo.jooq.domain.models.UserRes;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.demo.jooq.generated.Tables.USERS;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final DSLContext dslContext;

    /**
     * 모든 사용자 조회
     */
    public List<UserRes> findAllUsers() {
        return dslContext
                .selectFrom(USERS)
                .fetch()
                .into(UserRes.class);
    }

    /**
     * ID로 사용자 조회
     */
    public UserRes findById(Long id) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptionalInto(UserRes.class)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: ID = " + id));
    }

    /**
     * 사용자 저장 (생성/수정)
     */
    public UserRes save(UserReq user) {
        if (user.getId() == null) {
            // 새 사용자 생성
            return dslContext
                    .insertInto(USERS)
                    .set(USERS.LOGIN_ID, user.getLoginId())
                    .set(USERS.USER_NAME, user.getUserName())
                    .set(USERS.PICTURE, user.getPicture())
                    .set(USERS.PHONE_NUMBER, user.getPhoneNumber())
                    .set(USERS.MOBILE_PHONE_NUMBER, user.getMobilePhoneNumber())
                    .set(USERS.EMAIL, user.getEmail())
                    .set(USERS.CREATED_AT, LocalDateTime.now())
                    .returning()
                    .fetchOptional()  // fetchOne() 대신 fetchOptional() 사용
                    .map(r -> r.into(UserRes.class))
                    .orElseThrow(() -> new IllegalStateException("사용자 생성 실패"));
        } else {
            // 기존 사용자 수정
            return
                    dslContext
                            .update(USERS)
                            .set(USERS.LOGIN_ID, user.getLoginId())
                            .set(USERS.USER_NAME, user.getUserName())
                            .set(USERS.PICTURE, user.getPicture())
                            .set(USERS.PHONE_NUMBER, user.getPhoneNumber())
                            .set(USERS.MOBILE_PHONE_NUMBER, user.getMobilePhoneNumber())
                            .set(USERS.EMAIL, user.getEmail())
                            .set(USERS.LAST_UPDATED_AT, LocalDateTime.now())
                            .where(USERS.ID.eq(user.getId()))
                            .returning()
                            .fetchOptional()  // fetchOne() 대신 fetchOptional() 사용
                            .map(r -> r.into(UserRes.class))
                            .orElseThrow(() -> new IllegalStateException("사용자 수정 실패: " + user.getId()));

        }
    }

    /**
     * ID로 사용자 삭제
     */
    public void deleteById(Long id) {
        dslContext
                .deleteFrom(USERS)
                .where(USERS.ID.eq(id))
                .execute();
    }

    /**
     * ID 존재 여부 확인
     */
    public boolean existsById(Long id) {
        Integer count = dslContext
                .selectCount()
                .from(USERS)
                .where(USERS.ID.eq(id))
                .fetchOne(0, Integer.class);

        return count != null && count > 0;
    }

}