package com.agilevolve.web.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.agilevolve.domain.application.CardService;
import com.agilevolve.domain.common.security.CurrentUser;
import com.agilevolve.domain.model.card.Card;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.web.payload.AddCardPayload;
import com.agilevolve.web.payload.ChangeCardPositionsPayload;
import com.agilevolve.web.results.AddCardResult;
import com.agilevolve.web.results.ApiResult;
import com.agilevolve.web.results.Result;

@Controller
public class CardApiController {

  public CardService cardService;

  public CardApiController(CardService cardService) {
    this.cardService = cardService;
  }

  @PostMapping("/api/cards")
  public ResponseEntity<ApiResult> addCard(@RequestBody AddCardPayload payload, @CurrentUser SimpleUser currentUser) {
    Card card = cardService.addCard(payload.toCommand(currentUser.getUserId()));
    return AddCardResult.build(card);
  }

  @PostMapping("/api/cards/positions")
  public ResponseEntity<ApiResult> changeCardPositions(@RequestBody ChangeCardPositionsPayload payload) {
    
    cardService.changePositions(payload.toCommand());
    return Result.ok();
  }
}
