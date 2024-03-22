package com.agilevolve.domain.application.commands;

import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.UserId;

public class CreateBoardCommand {

  private UserId userId;
  private String name;
  private String description;
  private TeamId teamId;

  public CreateBoardCommand(UserId userId, String name, String description, TeamId teamID) {
    this.userId = userId;
    this.name = name;
    this.description = description;
    this.teamId = teamID;
  }

  public UserId getUserId() {
    return this.userId;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public TeamId getTeamId() {
    return this.teamId;
  }
}
