package com.agilevolve.web.apis;

import java.util.Date;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.agilevolve.config.SecurityConfiguration;
import com.agilevolve.domain.application.BoardService;
import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ContextConfiguration(classes = { SecurityConfiguration.class, MeApiController.class })
@WebMvcTest
public class MeApiControllerTests {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private TeamService teamService;

  @MockBean
  private BoardService boardService;

  private SimpleUser authenticatedUser;

  @BeforeEach
  public void setUp() throws IllegalAccessException {
    authenticatedUser = simpleUser();
  }

  @Test
  public void getMyData_blankPayload_shouldSucceedAndReturn201() throws Exception {

    Team mockTeam = Mockito.mock(Team.class);
    when(mockTeam.getId()).thenReturn(new TeamId(3L));
    when(mockTeam.getUserId()).thenReturn(new UserId(1L));
    when(mockTeam.getName()).thenReturn("Test Team");
    when(mockTeam.isArchived()).thenReturn(false);
    when(mockTeam.getCreatedDate()).thenReturn(new Date());

    Board mockBoard = Mockito.mock(Board.class);
    when(mockBoard.getId()).thenReturn(new BoardId(1L));
    when(mockBoard.getUserId()).thenReturn(new UserId(1L));
    when(mockBoard.getName()).thenReturn("Test Board");
    when(mockBoard.getDescription()).thenReturn("Test Description");
    when(mockBoard.getTeamId()).thenReturn(new TeamId(1L));

    when(teamService.findTeamsByUserId(any())).thenReturn(Collections.singletonList(mockTeam));
    when(boardService.findBoardsByMembership(any())).thenReturn(Collections.singletonList(mockBoard));

    mvc.perform(
        post("/api/me")
            .with(user(authenticatedUser))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.teams[0].id").value(3))
        .andExpect(jsonPath("$.teams[0].name").value("Test Team"))
        .andExpect(jsonPath("$.boards[0].id").value(1))
        .andExpect(jsonPath("$.boards[0].name").value("Test Board"))
        .andExpect(jsonPath("$.boards[0].description").value("Test Description"))
        .andExpect(jsonPath("$.boards[0].teamId").value(1))
        .andExpect(jsonPath("$.user.name").value("sunny"));
  }

  private SimpleUser simpleUser() throws IllegalAccessException {
    User user = User.create("sunny", "sunny@agilevolve.com", "MyPassword@");
    FieldUtils.writeField(user, "id", 1L, true);

    return new SimpleUser(user);
  }
}
