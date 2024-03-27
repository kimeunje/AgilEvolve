package com.agilevolve.web.payload;

import com.agilevolve.domain.application.commands.CreateBoardCommand;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.UserId;

import jakarta.validation.constraints.NotNull;

public class CreateBoardPayload {

  @NotNull
  private String name;

  @NotNull
  private String description;

  private long teamId;

  public CreateBoardCommand toCommand(UserId userId) {
    return new CreateBoardCommand(userId, name, description, new TeamId(teamId));
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTeamId(long teamId) {
    this.teamId = teamId;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public long getTeamId() {
    return this.teamId;
  }

}
