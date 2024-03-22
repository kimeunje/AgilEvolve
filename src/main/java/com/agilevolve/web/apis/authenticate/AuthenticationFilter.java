package com.agilevolve.web.apis.authenticate;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.agilevolve.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UsernamePasswordAuthenticationFilter 필터는
 * 콘텐츠 타입을 application/x-www-form-urlencoded로 예상하며,
 * 인증이 성공하면, 타깃 페이지로 사용자를 리다이렉트시키고
 * 인증이 실패하면 사용자를 에러 페이지로 리다이렉트시킨다.
 *
 * 프런트엔드에서는 Ajax로 콘텐츠 타입이 application/json인
 * 요청을 보내며 JSON 응답이 오기를 예상한다.
 *
 * 따라서, AbstractAuthenticationProcessingFilter를 상속받아
 * 프런트엔드에 동작에 맞게 커스터마이징한다.
 */
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

  private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
  private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
      .getContextHolderStrategy();

  public AuthenticationFilter() {
    super(new AntPathRequestMatcher("/api/authentications", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {

    log.debug("로그인 요청 진행중입니다.");

    String requestBody = IOUtils.toString(request.getReader());
    LoginRequest loginRequest = JsonUtils.toObject(requestBody, LoginRequest.class);
    if (loginRequest == null || loginRequest.isInvalid()) {
      throw new InsufficientAuthenticationException("유효하지 않은 인증 요청입니다.");
    }

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.username,
        loginRequest.password);
    Authentication authentication = this.getAuthenticationManager().authenticate(token);
    SecurityContext context = securityContextHolderStrategy.createEmptyContext();
    context.setAuthentication(authentication);
    securityContextHolderStrategy.setContext(context);
    securityContextRepository.saveContext(context, request, response);

    return authentication;
  }

  static class LoginRequest {
    private String username;
    private String password;

    public boolean isInvalid() {
      return StringUtils.isBlank(username) || StringUtils.isBlank(password);
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}
