package com.agilevolve.domain.common.security;

import java.lang.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * 스프링의 authentication principal 을 가져오는 커스텀 어노테이션
 * {@link org.springframework.security.core.context.SecurityContext}.
 *
 * <p>
 * 참조:
 * https://docs.spring.io/spring-security/site/docs/current/reference/html/mvc.html#mvc-authentication-principal
 * </p>
 */
@Target({ ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}
