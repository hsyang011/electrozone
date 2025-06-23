# 💻 ELECTROZONE

[🔗 서비스 바로가기](http://electrozone-env.eba-mn6ctxyx.ap-northeast-2.elasticbeanstalk.com/)

**ElectroZone**은 컴퓨터 부품, 노트북, 모니터, 오디오 장비, 완제품 PC 등을 판매하는 온라인 전자상거래 플랫폼입니다.  
사용자 편의성과 관리자의 효율성을 함께 고려한 실용적인 쇼핑몰 구현을 목표로 했습니다.

---

## 📋 주요 기능

### 1. 회원 관리
- JWT 기반 로그인 및 리프레시 토큰 재발급
- 구글/카카오 OAuth2 로그인 연동
- 이메일 인증 및 비밀번호 찾기 기능
- 회원 정보 수정 및 탈퇴 기능

### 2. 상품 및 주문 관리
- 상품 목록, 상세 조회 기능
- 장바구니 및 결제 기능 (I’mport API 연동)
- 주문 정보 저장 및 주문 내역 확인

### 3. 실시간 채팅 기능
- WebSocket + STOMP 기반 실시간 1:1 고객센터 채팅
- JWT 인증 기반 접근 제어
- 관리자/사용자 간 메시지 송수신 및 이전 메시지 불러오기

### 4. 자동 크롤링 및 상품 정보 업데이트
- 스케줄링된 크롤링을 통한 상품 정보 갱신
- AOP 기반 변경 감지 및 변경 로그 기록

### 5. CI/CD 및 클라우드 배포
- GitHub Actions 기반 CI/CD 자동화
- AWS Elastic Beanstalk 환경에 배포

---

## 🛠️ 사용 기술

- **Backend**: Java 21, Spring Boot 3.4.0, Spring Security, JPA, Spring AOP, JWT, OAuth2
- **Frontend**: HTML5, CSS3, Thymeleaf
- **Realtime**: WebSocket, STOMP
- **Database**: MySQL 8.0
- **Infra/DevOps**: GitHub Actions, AWS Elastic Beanstalk
- **API Integration**: Kakao, Google OAuth, I’mport (결제)

---

## 🖼️ 프로젝트 구조
```plaintext
src/
├── main/
│   ├── java/me/yangsongi/electrozone/
│   │   ├── config/        # 시큐리티, JWT, OAuth2, 웹소켓 설정
│   │   ├── controller/    # REST API 및 View Controller
│   │   ├── domain/        # 엔티티 클래스
│   │   ├── dto/           # 요청/응답 DTO
│   │   ├── repository/    # JPA Repository
│   │   ├── service/       # 비즈니스 로직
│   │   └── scheduler/     # 크롤링 스케줄러
│   └── resources/
│       ├── templates/     # Thymeleaf 템플릿
│       ├── static/        # CSS, JS 등 정적 리소스
│       └── application.yml
└── test/
    └── java/me/yangsongi/electrozone/
        ├── config/
        ├── controller/
        └── service/

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

### 1. 양방향 연관관계 순환 참조 문제
- 문제 : User → Product → User → ... 형태의 순환 참조가 발생
- 원인 : JPA에서 @ManyToOne, @OneToMany 등 양방향 연관관계를 설정한 상태에서 Entity를 그대로 JSON으로 변환
- 해결 : 별도의 DTO를 만들어 필요한 속성 값만 정의

### 2. 스케줄링 크롤링 작업 중 낙관적 락 문제
- 문제 : 상품 정보를 INSERT할 때 낙관적 락 발생
- 원인 : 자동으로 생성되는 id값에 대해서 prod_id값을 대입하려고 했기 때문에 save시점에서 update도 동시에 일어남
- 해결 : id필드에 GenerationType.IDENTITY 애노테이션 제거

### 3. OAuth2 인증 문제
- 원인 : 구글, 카카오의 인증 방식이 상이함
- 해결 : 각 프로바이더 별로 OAuth2User의 값을 파싱

### 4. JWT + SSR 방식의 타임리프 조합의 한계
- 문제 :  ****GET 요청 시 ****뷰 ****페이지에서 Principal을 주입받지 못함
- 원인 : GET 요청의 경우, JWT 토큰을 헤더에 담아 보낼 수 없어, 사용자 식별이 불가능함
- 해결 : 우선 뷰 페이지를 반환한 후, 페이지 로딩 시 JWT 인증 요청을 하여 현재 로그인 된 사용자 정보를 가져오도록 설계

### 5. WebSocket 구독 해제 처리 문제
- 원인: 관리자가 채팅방을 여러 번 이동 시 중복 구독 발생
- 해결: 채팅방 입장 시 이전 구독을 해제한 후 새로운 구독을 수행하여 중복 수신 방지
