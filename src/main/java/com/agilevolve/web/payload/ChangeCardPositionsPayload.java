package com.agilevolve.web.payload;

import java.util.List;

import com.agilevolve.domain.application.commands.ChangeCardPositionsCommand;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.card.CardPosition;

public class ChangeCardPositionsPayload {

	private long boardId;
	private List<CardPosition> cardPositions;

	public ChangeCardPositionsCommand toCommand() {
		return new ChangeCardPositionsCommand(new BoardId(boardId), cardPositions);
	}

	public long getBoardId() {
		return boardId;
	}

	public List<CardPosition> getCardPositions() {
		return cardPositions;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public void setCardPositions(List<CardPosition> cardPositions) {
		this.cardPositions = cardPositions;
	}

}
