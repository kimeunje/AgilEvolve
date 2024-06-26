package com.agilevolve.domain.model.cardlist;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

import com.agilevolve.domain.common.model.AbstractBaseEntity;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.user.UserId;

@Entity
@Table(name = "card_list")
public class CardList extends AbstractBaseEntity {

    private static final long serialVersionUID = -441685080L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id")
    private long boardId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position")
    private int position;

    @Column(name = "archived")
    private boolean archived;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    public static CardList create(BoardId boardId, UserId userId, String name, int position) {
        CardList cardList = new CardList();
        cardList.boardId = boardId.value();
        cardList.userId = userId.value();
        cardList.name = name;
        cardList.position = position;
        cardList.archived = false;
        cardList.createdDate = new Date();
        return cardList;
    }

    public void changeArchived(boolean archived) {
        this.archived = archived;
    }

    public CardListId getId() {
        return new CardListId(id);
    }

    public BoardId getBoardId() {
        return new BoardId(boardId);
    }

    public UserId getUserId() {
        return new UserId(userId);
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public boolean isArchived() {
        return archived;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CardList)) {
            return false;
        }
        CardList cardList = (CardList) o;
        return boardId == cardList.boardId &&
                position == cardList.position &&
                archived == cardList.archived &&
                Objects.equals(name, cardList.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, name, position, archived);
    }

    @Override
    public String toString() {
        return "CardList{" +
                " id='" + getId() + "'" +
                ", boardId='" + getBoardId() + "'" +
                ", userId='" + getUserId() + "'" +
                ", name='" + getName() + "'" +
                ", position='" + getPosition() + "'" +
                ", archived='" + isArchived() + "'" +
                ", createdDate='" + getCreatedDate() + "'" +
                "}";
    }
}
