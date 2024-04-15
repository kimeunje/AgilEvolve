# AgilEvolve

[https://agilevolve.site](https://agilevolve.site)

웹 기반 칸반 보드 툴 Trello(트렐로) 클론 프로젝트
> [_Building applications with Spring 5 and Vue.js 2: A real-world practical guide to building a modern full-stack web application_](https://www.amazon.com/Building-applications-Spring-5-0-Vue-js-ebook/dp/B079X1VTST).  
> [저자 James J. Ye, 2018] 책을 참고하여 프로젝트를 진행했습니다.

## 프로젝트 내용

### 개요

웹 서비스에서 주로 쓰이는 아키텍처 분석 및 디자인 패턴을 고려한 코드를 공부하기 위해 프로젝트 진행

### 일정

- 개인 프로젝트
- 2024/03/12 ~ 진행 중

### 사용 기술

- Spring Boot 3
- MySQL 8.0
- Vue 3

### 구현 기능
- 사용자 요구 사항을 사용자 스토리를 이용해 작성하였습니다.[[자세히]](https://blog.naver.com/kimeunje320/223389396838)  
- 사용자 요구 사항에 맞게 데이터 요구 사항을 작성하여 모델링 하였습니다. [[자세히]](https://blog.naver.com/kimeunje320/223387122937)  
![agilevolve data modeling](https://github.com/kimeunje/AgilEvolve/assets/143335772/735bf090-dffd-4630-b0e2-824539d6f515)  

- 3 레이어 아키텍처를 기반으로 애플리케이션을 설계하였습니다.
![agilevolve 아키텍처_700 drawio](https://github.com/kimeunje/AgilEvolve/assets/143335772/8f8f1244-150a-4d4e-86ce-9cc4e905a7c2)  
- 화면은 트렐로를 참고하여 설계하였습니다.
- API는 Rest 아키텍처를 준수하는 Restful API로 설계하였습니다. [[자세히]](https://blog.naver.com/kimeunje320/223406887324)
- 테스트는 빌드시 백엔드와 프런트엔드를 합쳐서 진행되게 자동화 하였습니다. [[자세히]](https://blog.naver.com/kimeunje320/223387119076)
- 스프링 시큐리티를 이용해 내부 API에 대해 보안을 강화했습니다. [[자세히]](https://blog.naver.com/kimeunje320/223391759916)  
- API에 들어오는 요청의 값을 검증하는 로직을 작성하였습니다. [[자세히]](https://blog.naver.com/kimeunje320/223389550943)  

### 문제 해결
- JPA N + 1 문제를 해결하기 위해서 우회하는 방식을 사용하였습니다. [[자세히]](https://blog.naver.com/kimeunje320/223404392249)

- 데이터 계층 테스트를 하기 위해 실제 데이터베이스가 아닌 H2 인메모리 데이터베이스를 이용하였습니다.

- API 응답에 대해 HashMap을 상속받은 ApiResult를 Builder 패턴을 사용해 작성해 가독성을 높였습니다.

- 3 레이어 아키텍처에서 계층마다 테스트를 구분하기 위해 헥사고날 아키텍처에서 일부 아이디어를 가져왔습니다.  

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

### 개선 사항
- 카드 페이지 설계 및 기능 구현
- 실시간 화면 공유
- 메시지 기반 비동기 처리
- CI/CD 자동화
