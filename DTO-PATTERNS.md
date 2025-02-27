# 🤔 Spring Boot에서 사용자 CRUD DTO 네이밍 패턴에 대한 고찰

평소 개발을 하면서 가장 고민되는 부분 중 하나가 네이밍이었다. 특히 CRUD API를 만들면서 DTO 클래스의 이름을 어떻게 지어야 할지 항상 고민이었는데, 이번에 User(사용자) 도메인을 개발하면서 이 부분에 대해 깊이있게 고민해봤다.

## 🌱 첫 번째 시도

가장 먼저 시도한 건 아주 단순한 접근이었다.

```java
// 초기 접근 방식
UserDto             // 기본 DTO
CreateUserDto       // 생성용
UpdateUserDto       // 수정용
UserSearchDto       // 검색용
```

근데 이건 뭔가 통일성도 없고, 명확하지도 않았다. `UserDto`는 너무 일반적이라 어떤 용도인지 전혀 알 수가 없었고, 나머지도 일관된 패턴이 없어보였다.

## 💫 두 번째 시도 - 접미사 통일

그래서 접미사라도 통일해보기로 했다.

```java
UserRequestDto      // 요청용
UserResponseDto     // 응답용
UserSearchDto       // 검색용
```

이것도 문제가 있었다:
- 요청이 생성인지 수정인지 구분할 수가 없음
- Request, Response 같은 접미사가 너무 길고 반복적
- HTTP 통신 레이어의 용어를 도메인 레이어에서 쓰는게 맞나? 라는 의문

## 💡 Command와 Condition의 발견

그러다 발견한 패턴이 Command와 Condition이었다.

```java
// 요청 DTO
UserCondition        // 검색 조건용
UserCreateCommand    // 생성용
UserUpdateCommand    // 수정용
```

이건 꽤 괜찮아 보였다:
- Condition은 검색/조회를 위한 조건이라는 의미가 명확
- Command는 상태 변경을 위한 명령이라는 의미가 잘 드러남
- CQRS 패턴과도 잘 어울림

## 🎨 응답 DTO의 고민

요청은 해결했는데, 이제 응답 쪽 이름을 어떻게 할지가 문제였다.
처음에는 이렇게 시도했다:

```java
UserResponse        // 기본 응답
UserListResponse    // 목록 응답
```

근데 `List<UserListResponse>` 같은 형태가 되니까 너무 이상했다. 
목록의 각 항목이 `UserListResponse`라는게 말이 안됐다.

한참을 고민하다가 이런 패턴으로 정리했다:

```java
// 요청 DTO
UserCondition        // 검색 조건용
UserCreateCommand    // 생성용
UserUpdateCommand    // 수정용

// 응답 DTO
UserView            // 기본 응답
UserDetailView      // 상세 정보
UserSummaryView     // 목록용 간단 정보
```

훨씬 자연스러워졌다:
- View는 클라이언트에게 보여질 정보라는 의미
- Summary는 목록에서 보여질 요약 정보라는 뜻이 명확
- `List<UserSummaryView>`로 써도 전혀 어색하지 않음

## ️ ⚙️ 최종 코드

실제 구현된 코드는 이런 모습이다:

```java
// 요청 DTO
@Getter @Setter
public class UserCondition {
    private String name;
    private String email;
}

@Getter @Setter
public class UserCreateCommand {
    private String name;
    private String email;
    private String password;
}

@Getter @Setter
public class UserUpdateCommand {
    private Long id;
    private String name;
    private String email;
}

// 응답 DTO
@Getter @Setter
public class UserSummaryView {
    private Long id;
    private String name;
    private String email;
}

@Getter @Setter
public class UserDetailView {
    private Long id;
    private String name;
    private String email;
    private String address;
    private List<OrderSummaryView> orders;
}
```

## 🎯 API 컨트롤러

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public List<UserSummaryView> searchUsers(UserCondition condition) {
        return userService.searchUsers(condition);
    }
    
    @GetMapping("/{id}")
    public UserDetailView getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
    
    @PostMapping
    public UserDetailView createUser(UserCreateCommand command) {
        return userService.createUser(command);
    }
    
    @PutMapping("/{id}")
    public UserDetailView updateUser(UserUpdateCommand command) {
        return userService.updateUser(command);
    }
}
```

## 🎉 결론

이렇게 정리된 네이밍 패턴의 장점은:

1. **의도가 명확하다**
   - Condition은 조회 조건
   - Command는 상태 변경 명령
   - View는 화면에 보여질 정보

2. **일관성이 있다**
   - 모든 요청/응답 DTO가 같은 패턴을 따름
   - 다른 도메인에도 똑같이 적용할 수 있음

3. **확장하기 좋다**
   - 새로운 기능이 추가돼도 같은 패턴으로 확장 가능
   - 팀 컨벤션으로 잡기 좋음

앞으로도 더 좋은 패턴이 있는지 계속 고민해볼 생각이다.