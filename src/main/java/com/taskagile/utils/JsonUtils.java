package com.taskagile.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 문자열로 변환하는 유틸리티 메서드
 */
public final class JsonUtils {

  private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

  private JsonUtils() {
  }

  public static String toJson(Object object) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("객체를 JSON 문자열로 변환하는데 실패하였습니다.", e);
      return null;
    }
  }
}
