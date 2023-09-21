package com.taskagile.utils;

import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 문자열 변환 유틸리티 메서드
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

  public static <T> T toObject(String json, Class<T> clazz) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      log.error("문자열을 변환하는데 실패하였습니다. 문자열: `" + json + "` 클래스: `" + clazz.getName() + "`", e);
      return null;
    }
  }

  public static void write(Writer writer, Object value) throws IOException {
    new ObjectMapper().writeValue(writer, value);
  }

}
