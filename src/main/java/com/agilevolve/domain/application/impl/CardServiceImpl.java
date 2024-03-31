package com.agilevolve.domain.application.impl;

import java.util.List;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.agilevolve.domain.application.CardService;
import com.agilevolve.domain.application.commands.AddCardCommand;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.card.Card;
import com.agilevolve.domain.model.card.CardRepository;

@Service
@Transactional
public class CardServiceImpl implements CardService {

  private CardRepository cardRepository;

  public CardServiceImpl(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Override
  public List<Card> findByBoardId(BoardId boardId) {
    return cardRepository.findByBoardId(boardId);
  }

  @Override
  public Card addCard(AddCardCommand command) {
    Card card = Card.create(command.getCardListId(), command.getUserId(), command.getTitle(), command.getPosition());
    cardRepository.save(card);
    return card;
  }

}
