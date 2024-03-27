package com.agilevolve.web.payload;

import com.agilevolve.domain.application.commands.CreateTeamCommand;
import com.agilevolve.domain.model.user.UserId;

import jakarta.validation.constraints.NotNull;

public class CreateTeamPayload {

  @NotNull
  private String name;

  public CreateTeamCommand toCommand(UserId userId) {
    return new CreateTeamCommand(userId, name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
