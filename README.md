# AgilEvolve

[https://agilevolve.site](https://agilevolve.site)

웹 기반 칸반 보드 툴 Trello(트렐로) 클론 프로젝트
> [_Building applications with Spring 5 and Vue.js 2: A real-world practical guide to building a modern full-stack web application_](https://www.amazon.com/Building-applications-Spring-5-0-Vue-js-ebook/dp/B079X1VTST).  
> [저자 James J. Ye, 2018] 책을 참고하여 프로젝트를 진행했습니다.

## Local 개발환경 설정

### 준비사항

- JDK17 - OpenJDK를 선호
- MAVEN 3.0+
- MySQL 8.0+

### 데이터베이스 설정

- 데이터베이스 `agilevolve` 생성
- setup 폴더에 있는 script 파일로 초기화

### dev properties 파일 추가

- `application.properties`의 설정을 `src/main/resources/application-dev.properties`파일을 생성해 다음 설정을 추가하여 오버라이드 해주세요.

```properties
// DB
spring.datasource.url=<your DB address>
spring.datasource.username=<your DB username>
spring.datasource.password=<your DB password>

// smtp
spring.mail.username=<your gmail smtp username>
spring.mail.password=<your gmail smtp password>
```

## 명령어

- Use `mvn test` 백엔드 및 프런트엔드 테스트
- Use `mvn spring-boot:run` 백엔드 서버 실행
- Use `npm run dev` 프런트엔드 개발 서버 실행 (`front-end` 폴더 안에서 실행해야 합니다.)
- Use `mvn install` 백엔드 및 프런트엔드 빌드
- Use `java -jar target/app-0.0.1-SNAPSHOT.jar` 번들된 애플리케이션 실행

## 프로젝트 내용

### 일정

- 개인 프로젝트
- 2024/03/12 ~ 진행 중

### 사용 기술

- Spring Boot 3.1.5
- Vue 3.3.4
- typescript 5.1.6
- vite 4.4.9

### 구현 기능

- [사용자 요구 사항](https://blog.naver.com/kimeunje320/223389396838)
- [데이터 모델링](https://blog.naver.com/kimeunje320/223387122937)
- API 설계
- [백엔드 테스트](https://blog.naver.com/kimeunje320/223387119076), 프런트엔드 테스트
- [인증, 권한부여](https://blog.naver.com/kimeunje320/223391759916)
- [클라이언트 데이터 검증](https://blog.naver.com/kimeunje320/223389550943)

### 구현 이미지

- 회원가입  
![회원가입](https://github.com/kimeunje/AgilEvolve/assets/143335772/58910912-f1f0-4b2c-b69e-a0737f6adf1a)

- 로그인  
![로그인](https://github.com/kimeunje/AgilEvolve/assets/143335772/c62e5df3-f095-4e8c-b342-25afd8cacee8)

- 메인화면  
![메인화면](https://github.com/kimeunje/AgilEvolve/assets/143335772/3a4dae40-df52-41a5-8cc7-622cd7fb8e95)

- 팀 생성  
![팀생성](https://github.com/kimeunje/AgilEvolve/assets/143335772/907fc5f5-b8b4-4abc-8446-1a8bd4c44228)

- 카드리스트 추가  
![카드리스트추가](https://github.com/kimeunje/AgilEvolve/assets/143335772/a2833fb8-ebab-4a95-be92-bc5c94eda9cb)

- 카드리스트 위치 변경  
![카드리스트위치](https://github.com/kimeunje/AgilEvolve/assets/143335772/d0fbe863-f751-4958-bd64-a57536ae4807)

- 카드 추가  
![카드추가](https://github.com/kimeunje/AgilEvolve/assets/143335772/9a78eb98-889b-4d74-a46c-7cc9b3ab5501)

- 카드 위치 변경  
![카드위치](https://github.com/kimeunje/AgilEvolve/assets/143335772/0d26566b-f0d0-45f0-af97-1a180a8f018d)

- 멤버 추가  
![멤버추가](https://github.com/kimeunje/AgilEvolve/assets/143335772/9a76e92e-b5af-4c7c-b05b-822ffe7abd47)

- 추가된 멤버 확인  
![멤버확인](https://github.com/kimeunje/AgilEvolve/assets/143335772/7b86ed55-6954-40c9-aba0-827678851186)

