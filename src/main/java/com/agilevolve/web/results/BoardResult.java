package com.agilevolve.web.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.team.Team;

public class BoardResult {

    public static ResponseEntity<ApiResult> build(Team team, Board board, List<CardList> cardLists) {
        Map<String, Object> boardData = new HashMap<>();
        boardData.put("id", board.getId().value());
        boardData.put("name", board.getName());
        boardData.put("personal", board.isPersonal());

        List<CardListData> cardListsData = new ArrayList<>();

        for (CardList cardList : cardLists) {
            cardListsData.add(new CardListData(cardList));
        }

        ApiResult result = ApiResult.blank()
                .add("board", boardData)
                .add("cardLists", cardListsData);

        if (!board.isPersonal()) {
            Map<String, String> teamData = new HashMap<>();
            teamData.put("name", team.getName());
            result.add("team", teamData);
        }
        return Result.ok(result);
    }

    private static class CardListData {
        private long id;
        private String name;
        private int position;

        CardListData(CardList cardList) {
            this.id = cardList.getId().value();
            this.name = cardList.getName();
            this.position = cardList.getPosition();
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPosition() {
            return position;
        }
    }
}
