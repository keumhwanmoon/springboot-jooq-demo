package com.demo.jooq.domain.controller;

import com.demo.jooq.domain.models.UserReq;
import com.demo.jooq.domain.models.UserRes;
import com.demo.jooq.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * 모든 사용자 조회
     *
     * @return 시스템에 있는 모든 사용자의 목록
     */
    @GetMapping
    public ResponseEntity<List<UserRes>> getUsers() {
        try {
            List<UserRes> users = userService.getAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    /**
     * 사용자 조회
     *
     * @param id 사용자 ID
     * @return 조회된 사용자 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserRes> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 새로운 사용자 생성
     *
     * @param user 생성할 사용자 정보
     * @return 생성된 사용자 정보
     */
    @PostMapping
    public ResponseEntity<UserRes> createUser(@Valid @RequestBody UserReq user) {
        try {
            UserRes createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 기존 사용자 정보 수정
     *
     * @param id   수정할 사용자 ID
     * @param user 수정할 사용자 정보
     * @return 수정된 사용자 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserRes> updateUser(@PathVariable Long id, @Valid @RequestBody UserReq user) {
        try {
            return userService.updateUser(id, user)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사용자 삭제
     *
     * @param id 삭제할 사용자 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            if (userService.deleteUser(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
