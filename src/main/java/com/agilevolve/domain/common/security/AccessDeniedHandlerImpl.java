package com.agilevolve.domain.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.agilevolve.domain.model.user.SimpleUser;

import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

  private final static Logger log = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    if (log.isDebugEnabled()) {
      log.debug("`" + request.getRequestURI() + "` 에 접근 금지 되었습니다.");
    }

    if (request.getRequestURI().startsWith("/api/")) {
      if (request.getUserPrincipal() instanceof SimpleUser) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
      } else {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      }
    } else {
      response.sendRedirect("/login");
    }
  }
}
