# ELECTRO ZONE

**Electrozone**은 컴퓨터 부품, 노트북, 모니터, 오디오 장비, 완제품 PC 등 전자기기를 판매하는 온라인 마켓플레이스입니다.  
사용자 친화적인 인터페이스와 강력한 백엔드 시스템을 통해 쇼핑 경험을 개선하는 것을 목표로 합니다.

---

## 📋 주요 기능

### 1. 회원 관리
- JWT 기반 인증 및 리프레시 토큰 적용
- 이메일 인증 및 비밀번호 찾기 기능 지원
- 사용자 정보 수정 및 삭제

### 2. 상품 관리
- 다양한 전자기기 상품 목록 조회
- 상품 상세 페이지 제공
- 장바구니 및 결제 기능

### 3. 결제
아임포트(I’mport) API를 활용한 결제 시스템.

### 4. 검색 및 필터링
- 상품 검색 및 카테고리별 필터링
- 가격, 평점 등을 기준으로 정렬 가능

---

## 🛠️ 사용 기술

- **Backend**: Java 21, Spring Boot 3.4.0, Spring Security, JPA
- **Frontend**: HTML5, CSS3, Thymeleaf
- **Database**: MySQL 8.0
- **DevOps**: Docker, Docker Compose
- **API Integration**: Kakao API (로그인), I’mport API (결제), Firebase (푸시 알림)
- **Testing**: JUnit, AssertJ, MockMvc
- **Others**: Git, IntelliJ, Postman

---

## 🖼️ 프로젝트 구조
```plaintext
src/
├── main/
│   ├── java/me/yangsongi/electrozone/
│   │   ├── config/       # JWT 설정 및 시큐리티 설정
│   │   ├── controller/   # REST API 및 View Controller
│   │   ├── domain/       # 엔티티 클래스
│   │   ├── dto/          # 요청 및 응답 DTO
│   │   ├── repository/   # JPA Repository
│   │   ├── service/      # 비즈니스 로직
│   └── resources/
│       ├── templates/    # Thymeleaf 템플릿
│       ├── static/       # 정적 파일 (CSS, JS, 이미지)
│       └── application.yml
└── test/
    ├── java/me/yangsongi/electrozone/
        ├── config/jwt/TokenProviderTest.java  # JWT 기능 테스트
        ├── controller/TokenApiControllerTest.java # API 테스트
```

## 🚀 설치 및 실행

### 1. 코드 클론
```bash
git clone https://github.com/hsyang011/electrozone.git
cd electrozone
```

### 2. Docker 환경 구성
- docker-compose.yml 파일 확인 및 설정 후 실행
```bash
docker-compose up -d
```

### 3. 애플리케이션 실행
- 로컬 실행: IntelliJ에서 애플리케이션 실행
- 서버 접속: http://localhost:8080

---

## ⚠️ 트러블슈팅

```

---

## 🚀 향후 계획
- Redis를 활용한 캐싱 도입.
- ElasticSearch로 검색 최적화.
- 추천 알고리즘 개선 및 개인화 기능 확장.
