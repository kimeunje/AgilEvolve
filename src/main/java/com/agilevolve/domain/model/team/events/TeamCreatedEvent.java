package com.agilevolve.domain.model.team.events;

import org.springframework.util.Assert;

import com.agilevolve.domain.common.event.DomainEvent;
import com.agilevolve.domain.model.team.Team;

public class TeamCreatedEvent extends DomainEvent {

  private static final long serialVersionUID = 893199059L;

  private Team team;

  public TeamCreatedEvent(Object source, Team team) {
    super(source);
    Assert.notNull(team, "`team` 파라미터는 null이면 안됩니다.");
    this.team = team;
  }

  public Team getTeam() {
    return team;
  }
}
