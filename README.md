# AgilEvolve

오픈 소스 작업 관리 도구
built with Vue.js 3, Spring Boot 3, and MySQL 8.0+

## Local development

`application.properties`의 설정을 덮어쓴 `src/main/resources/application-dev.properties`파일을 생성해주세요.

```properties
// DB
spring.datasource.url=<your DB address>
spring.datasource.username=<your DB username>
spring.datasource.password=<your DB password>

// smtp
spring.mail.username=<your gmail smtp username>
spring.mail.password=<your gmail smtp password>
```

## Commands

- Use `mvn install` 백엔드 및 프런트엔드 빌드
- Use `mvn test` 백엔드 및 프런트엔드 테스트
- Use `mvn spring-boot:run` 백엔드 개발 서버 실행
- Use `npm run dev` 프런트엔드 개발 서버 실행  
** `front-end` 폴더에서 실행
- Use `java -jar target/app-0.0.1-SNAPSHOT.jar` 백엔드 및 프런트엔드 배포