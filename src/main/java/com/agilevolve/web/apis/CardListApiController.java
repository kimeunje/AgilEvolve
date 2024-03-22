package com.agilevolve.web.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.agilevolve.domain.application.CardListService;
import com.agilevolve.domain.common.security.CurrentUser;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.web.payload.AddCardListPayload;
import com.agilevolve.web.results.AddCardListResult;
import com.agilevolve.web.results.ApiResult;

@Controller
public class CardListApiController {

    private CardListService cardListService;

    public CardListApiController(CardListService cardListService) {
        this.cardListService = cardListService;
    }

    @PostMapping("/api/card-lists")
    public ResponseEntity<ApiResult> addCardList(@RequestBody AddCardListPayload payload,
            @CurrentUser SimpleUser currentUser) {
        CardList cardList = cardListService.addCardList(payload.toCommand(currentUser.getUserId()));
        return AddCardListResult.build(cardList);
    }
}
