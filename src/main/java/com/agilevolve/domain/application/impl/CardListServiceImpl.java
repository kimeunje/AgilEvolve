package com.agilevolve.domain.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.agilevolve.domain.application.CardListService;
import com.agilevolve.domain.application.commands.AddCardListCommand;
import com.agilevolve.domain.application.commands.ChangeCardListArchivedCommand;
import com.agilevolve.domain.application.commands.ChangeCardListPositionsCommand;
import com.agilevolve.domain.common.event.DomainEventPublisher;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.cardlist.CardListId;
import com.agilevolve.domain.model.cardlist.CardListRepository;
import com.agilevolve.domain.model.cardlist.events.CardListAddedEvent;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CardListServiceImpl implements CardListService {

    private CardListRepository cardListRepository;
    private DomainEventPublisher domainEventPublisher;

    public CardListServiceImpl(CardListRepository cardListRepository, DomainEventPublisher domainEventPublisher) {
        this.cardListRepository = cardListRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public List<CardList> findByBoardId(BoardId boardId) {
        return cardListRepository.findByBoardId(boardId);
    }

    @Override
    public CardList addCardList(AddCardListCommand command) {
        CardList cardList = CardList.create(command.getBoardId(), command.getUserId(), command.getName(),
                command.getPosition());

        cardListRepository.save(cardList);
        domainEventPublisher.publish(new CardListAddedEvent(this, cardList));
        return cardList;
    }

    @Override
    public void changePositions(ChangeCardListPositionsCommand command) {
        cardListRepository.changePositions(command.getCardListPositions());
    }

    @Override
    public void changeArchived(ChangeCardListArchivedCommand command) {
        Assert.notNull(command, "파라미터 `command`는 필수로 존재해야 합니다.");

        CardList cardList = findCardList(command.getCardListId());
        cardList.changeArchived(command.isArchived());
        cardListRepository.save(cardList);
    }

    private CardList findCardList(CardListId cardListId) {
        CardList cardList = cardListRepository.findById(cardListId);
        Assert.notNull(cardList, "카드 리스트 id의 " + cardList + "가 존재해야 합니다.");
        return cardList;
    }
}
