package com.agilevolve.domain.application.impl;

import java.util.List;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.application.commands.CreateTeamCommand;
import com.agilevolve.domain.common.event.DomainEventPublisher;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.team.TeamRepository;
import com.agilevolve.domain.model.team.events.TeamCreatedEvent;
import com.agilevolve.domain.model.user.UserId;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

  private TeamRepository teamRepository;
  private DomainEventPublisher domainEventPublisher;

  public TeamServiceImpl(TeamRepository teamRepository, DomainEventPublisher domainEventPublisher) {
    this.teamRepository = teamRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Override
  public List<Team> findTeamsByUserId(UserId userId) {
    return teamRepository.findTeamsByUserId(userId);
  }

  @Override
  public Team findById(TeamId teamId) {
    return teamRepository.findById(teamId);
  }

  @Override
  public Team createTeam(CreateTeamCommand command) {
    Team team = Team.create(command.getName(), command.getUserId());
    teamRepository.save(team);
    domainEventPublisher.publish(new TeamCreatedEvent(this, team));
    return team;
  }

}
