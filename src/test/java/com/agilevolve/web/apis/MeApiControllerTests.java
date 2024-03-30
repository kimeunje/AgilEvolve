package com.agilevolve.web.apis;

import java.util.List;
import java.util.ArrayList;

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
import com.agilevolve.domain.application.UserService;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ContextConfiguration(classes = { SecurityConfiguration.class, MeApiController.class })
@WebMvcTest
public class MeApiControllerTests {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

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

    User user = new User();
    List<Team> teams = new ArrayList<>();
    List<Board> boards = new ArrayList<>();

    when(userService.findById(authenticatedUser.getUserId())).thenReturn(user);
    when(teamService.findTeamsByUserId(authenticatedUser.getUserId())).thenReturn(teams);
    when(boardService.findBoardsByMembership(authenticatedUser.getUserId())).thenReturn(boards);

    if (authenticatedUser == null) {
      return;
    }
    mvc.perform(
        post("/api/me")
            .with(user(authenticatedUser))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isOk());
  }

  private SimpleUser simpleUser() throws IllegalAccessException {
    User user = User.create("sunny", "sunny@agilevolve.com", "MyPassword@");
    FieldUtils.writeField(user, "id", 1L, true);

    return new SimpleUser(user);
  }
}
