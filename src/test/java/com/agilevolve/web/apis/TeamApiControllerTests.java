package com.agilevolve.web.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.agilevolve.config.SecurityConfiguration;
import com.agilevolve.domain.application.BoardService;
import com.agilevolve.domain.application.CardListService;
import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.application.commands.CreateBoardCommand;
import com.agilevolve.domain.application.commands.CreateTeamCommand;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;
import com.agilevolve.utils.JsonUtils;
import com.agilevolve.web.payload.CreateBoardPayload;
import com.agilevolve.web.payload.CreateTeamPayload;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ContextConfiguration(classes = { SecurityConfiguration.class, TeamApiController.class })
@WebMvcTest
public class TeamApiControllerTests {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private TeamService teamService;

  private SimpleUser authenticatedUser;

  @BeforeEach
  public void setUp() throws IllegalAccessException {
    authenticatedUser = simpleUser();
  }

  @Test
  public void createTeam_blankPayload_shouldFailAndReturn400() throws Exception {
    mvc.perform(post("/api/teams")
        .with(user(authenticatedUser)))
        .andExpect(status().is(400));
  }

  @Test
  public void createTeam_validPayload_shouldSucceedAndReturn201() throws Exception {

    CreateTeamPayload mockPayload = Mockito.mock(CreateTeamPayload.class);
    when(mockPayload.getName()).thenReturn("Test Team");

    Team mockTeam = Mockito.mock(Team.class);
    when(mockTeam.getId()).thenReturn(new TeamId(3L));
    when(mockTeam.getUserId()).thenReturn(new UserId(1L));
    when(mockTeam.getName()).thenReturn("Test Team");
    when(mockTeam.isArchived()).thenReturn(false);
    when(mockTeam.getCreatedDate()).thenReturn(new Date());

    when(teamService.createTeam(any(CreateTeamCommand.class))).thenReturn(mockTeam);

    mvc.perform(
        post("/api/teams")
            .with(Objects.requireNonNull(user(authenticatedUser)))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(Objects.requireNonNull(JsonUtils.toJson(mockPayload))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Test Team"))
        .andExpect(jsonPath("$.id").value(3));
  }

  private SimpleUser simpleUser() throws IllegalAccessException {
    User user = User.create("sunny", "sunny@agilevolve.com", "MyPassword@");
    FieldUtils.writeField(user, "id", 1L, true);

    return new SimpleUser(user);
  }
}
