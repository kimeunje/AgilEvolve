package com.agilevolve.web.payload;

import com.agilevolve.domain.application.commands.AddCardListCommand;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.user.UserId;

public class AddCardListPayload {

    private long boardId;
    private String name;
    private int position;

    public AddCardListCommand toCommand(UserId userId) {
        return new AddCardListCommand(userId, name, new BoardId(boardId), position);
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getBoardId() {
        return this.boardId;
    }

    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return this.position;
    }

}
