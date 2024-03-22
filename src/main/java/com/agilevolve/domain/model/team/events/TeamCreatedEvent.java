package com.agilevolve.domain.model.team.events;

import com.agilevolve.domain.common.event.DomainEvent;
import com.agilevolve.domain.model.team.Team;

public class TeamCreatedEvent extends DomainEvent {

  private static final long serialVersionUID = 893199059L;

  private Team team;

  public TeamCreatedEvent(Object source, Team team) {
    super(source);
    this.team = team;
  }

  public Team getTeam() {
    return team;
  }
}
