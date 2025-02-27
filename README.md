# 🚀 Spring Boot with JOOQ Demo Project

이 프로젝트는 Spring Boot 환경에서 JOOQ(Java Object Oriented Querying)를 활용하는 방법을 보여주는 데모 프로젝트입니다.  
JOOQ를 사용하여 타입 안전성이 보장된 SQL 쿼리를 작성하고, 데이터베이스 스키마를 기반으로 자동 생성된 코드를 활용하는 예제를 제공합니다.

## 🧑‍💻 작성자 소개

안녕하세요! 15년차 백엔드 개발자 문금환입니다.  
현재 프리랜서로 활동하고 있으며, 코틀린과 스프링을 주력으로 사용하고 있습니다.  
더 나은 코드를 위해 항상 고민하고 새로운 기술을 탐구하는 것을 즐깁니다.

궁금하신 점이나 협업 제안이 있으시다면 언제든 연락 주세요.

### 연락처
- 이메일: keumhwan.moon@gmail.com
- GitHub: [@keumhwanmoon](https://github.com/keumhwanmoon)

### 기술 블로그
- [Tistory](https://jason-moon.tistory.com/)
- [Medium](https://medium.com/@jason.moon.kr)
- [GitHub Pages](https://keumhwanmoon.github.io)

## ⚙️ 기술 스택

- Java 17 (OpenJDK)
- Spring Boot 3.x
- JOOQ
- Spring MVC
- H2 Database
- Lombok
- Gradle
- Spring REST Docs

## 📁 프로젝트 구조

```text
src
├── main
│   ├── java
│   │   └── com.example.demo
│   │       ├── config          # 설정 클래스
│   │       │   └── JooqConfig.java
│   │       ├── controller      # REST 컨트롤러
│   │       │   └── UserController.java
│   │       ├── service         # 비즈니스 로직
│   │       │   ├── UserService.java
│   │       │   └── UserServiceImpl.java
│   │       └── repository      # JOOQ 리포지토리
│   │           └── UserRepository.java
│   └── resources
│       ├── application.yml     # 애플리케이션 설정
│       ├── schema.sql         # DB 스키마
│       └── data.sql          # 초기 데이터
├── test
│   └── java
│       └── com.example.demo
│           ├── controller      # 컨트롤러 테스트
│           ├── service         # 서비스 테스트
│           └── repository      # 리포지토리 테스트
└── http                    # HTTP 요청 테스트 파일
    ├── http-client.env.json
    └── user-api.http
```

## 🌟 주요 기능

### 👥 사용자 관리 (User Management)
- 사용자 목록 조회 (`GET /api/users`)
- 사용자 상세 조회 (`GET /api/users/{id}`)
- 사용자 생성 (`POST /api/users`)
- 사용자 수정 (`PUT /api/users/{id}`)
- 사용자 삭제 (`DELETE /api/users/{id}`)

## 🚀 시작하기

### ✅ 필수 요구사항
- JDK 17 이상
- Gradle 7.x 이상

### 📥 설치 및 실행

1. 프로젝트 Clone
```bash
git clone https://github.com/keumhwanmoon/springboot-jooq-demo
cd springboot-jooq-demo
```

2. 프로젝트 빌드
```bash
./gradlew clean build
```

3. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 🔄 JOOQ 클래스 생성

1. JOOQ 클래스 생성 실행
```bash
./gradlew jooqGenerate
```

2. 생성된 클래스 확인
- 생성된 클래스는 다음 경로에서 확인할 수 있습니다:
  - `build/generated-sources/jooq`
- 주요 생성 파일:
  - Tables: 데이터베이스 테이블에 대응하는 클래스
  - Records: 각 테이블의 레코드를 표현하는 클래스
  - Sequences: 데이터베이스 시퀀스
  - Keys: 기본 키와 외래 키 정의
  - Indexes: 테이블 인덱스 정보

> 참고: jooqGenerate 태스크는 `src/main/resources`의 `schema.sql`을 기반으로 코드를 생성합니다. 스키마 변경 시 재실행이 필요합니다.

## 💾 데이터베이스 구성

프로젝트는 H2 인메모리 데이터베이스를 사용합니다:
- `schema.sql`: 테이블 스키마 정의
- `data.sql`: 초기 데이터 설정

### 주요 테이블
1. USERS 테이블
   - 사용자 정보 관리
   - 로그인 ID, 이름, 연락처 등 저장

## 📚 API 문서

### REST Docs를 통한 API 문서 확인 방법

1. 프로젝트 빌드 후 문서 생성
```bash
./gradlew clean build
```

2. 문서 확인
- 생성된 API 문서는 다음 경로에서 확인 가능:
  - `build/docs/asciidoc/index.html`

3. API 문서 웹 접속
- 애플리케이션 실행 후 다음 URL에서 문서 확인 가능:
  - `http://localhost:8080/docs/index.html`

## HTTP 테스트 방법

프로젝트의 `/http` 폴더에서 HTTP Request를 통해 API를 테스트할 수 있습니다.

### IDE 지원
#### VS Code
1. **확장 프로그램 설치**
   - `REST Client` 확장 프로그램 설치 필요
   - VS Code 마켓플레이스에서 "REST Client" 검색 후 설치

2. **사용 방법**
   - `.http` 또는 `.rest` 파일 열기
   - 각 요청 위의 `Send Request` 링크 클릭
   - 단축키: `Ctrl + Alt + R` (Windows/Linux) 또는 `Cmd + Alt + R` (Mac)

#### IntelliJ IDEA
1. 기본 내장된 HTTP Client 사용
2. 각 Request 옆의 실행 버튼(▶) 클릭

### 환경 설정
- 환경 설정 파일: `/http/http-client.env.json`
```json
{
  "local": {
    "host": "http://localhost",
    "port": "8080"
  }
}
```

💡 **참고**: VS Code의 REST Client는 IntelliJ의 HTTP Client와 호환되므로, 
동일한 `.http` 파일을 두 IDE에서 모두 사용할 수 있습니다.

## 📝 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다.  
자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.