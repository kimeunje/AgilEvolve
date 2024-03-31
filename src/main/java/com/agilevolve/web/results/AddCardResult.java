package com.agilevolve.web.results;

import org.springframework.http.ResponseEntity;

import com.agilevolve.domain.model.card.Card;

public class AddCardResult {

  public static ResponseEntity<ApiResult> build(Card card) {
    ApiResult apiResult = ApiResult.blank()
        .add("id", card.getId().value())
        .add("title", card.getTitle())
        .add("position", card.getPosition());
    return Result.ok(apiResult);
  }
}
