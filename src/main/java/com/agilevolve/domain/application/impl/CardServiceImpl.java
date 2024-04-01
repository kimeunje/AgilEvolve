package com.agilevolve.domain.application.impl;

import java.util.List;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.agilevolve.domain.application.CardService;
import com.agilevolve.domain.application.commands.AddCardCommand;
import com.agilevolve.domain.application.commands.ChangeCardPositionsCommand;
import com.agilevolve.domain.common.event.DomainEventPublisher;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.card.Card;
import com.agilevolve.domain.model.card.CardRepository;
import com.agilevolve.domain.model.card.events.CardAddedEvent;

@Service
@Transactional
public class CardServiceImpl implements CardService {

  private CardRepository cardRepository;
  private DomainEventPublisher domainEventPublisher;

  public CardServiceImpl(CardRepository cardRepository, DomainEventPublisher domainEventPublisher) {
    this.cardRepository = cardRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Override
  public List<Card> findByBoardId(BoardId boardId) {
    return cardRepository.findByBoardId(boardId);
  }

  @Override
  public Card addCard(AddCardCommand command) {
    Card card = Card.create(command.getCardListId(), command.getUserId(), command.getTitle(), command.getPosition());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardAddedEvent(this, card));
    return card;
  }

  @Override
  public void changePositions(ChangeCardPositionsCommand command) {
    cardRepository.changePositions(command.getCardPositions());
  }

}
