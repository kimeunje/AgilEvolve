package com.agilevolve.domain.application.commands;

import com.agilevolve.domain.model.user.UserId;

public class CreateTeamCommand {

  private UserId userId;
  private String name;

  public CreateTeamCommand(UserId userId, String name) {
    this.userId = userId;
    this.name = name;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }
}
