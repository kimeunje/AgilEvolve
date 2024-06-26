package com.agilevolve.domain.model.card;

import java.util.Date;
import java.util.Objects;

import com.agilevolve.domain.common.model.AbstractBaseEntity;
import com.agilevolve.domain.model.cardlist.CardListId;
import com.agilevolve.domain.model.user.UserId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "card")
public class Card extends AbstractBaseEntity {

  private static final long serialVersionUID = 4245958423L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "card_list_id")
  private long cardListId;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "position")
  private int position;

  @Column(name = "archived")
  private boolean archived;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  public static Card create(CardListId cardListId, UserId userId, String title, int position) {
    Card card = new Card();
    card.cardListId = cardListId.value();
    card.userId = userId.value();
    card.title = title;
    card.description = "";
    card.position = position;
    card.archived = false;
    card.createdDate = new Date();
    return card;
  }

  public CardId getId() {
    return new CardId(id);
  }

  public CardListId getCardListId() {
    return new CardListId(cardListId);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
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
    if (this == o)
      return true;
    if (!(o instanceof Card))
      return false;
    Card card = (Card) o;
    return cardListId == card.cardListId &&
        userId == card.userId &&
        position == card.position &&
        archived == card.archived &&
        Objects.equals(title, card.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardListId, userId, title, position, archived);
  }

  @Override
  public String toString() {
    return "Card{" +
        "id=" + id +
        ", cardListId=" + cardListId +
        ", userId=" + userId +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", position=" + position +
        ", archived=" + archived +
        ", createdDate=" + createdDate +
        '}';
  }
}
