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
import com.agilevolve.domain.application.CardService;
import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.application.commands.CreateBoardCommand;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;
import com.agilevolve.utils.JsonUtils;
import com.agilevolve.web.payload.CreateBoardPayload;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ContextConfiguration(classes = { SecurityConfiguration.class, BoardApiController.class })
@WebMvcTest
public class BoardApiControllerTests {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private BoardService boardService;

  @MockBean
  private TeamService teamService;

  @MockBean
  private CardListService cardListService;

  @MockBean
  private CardService cardService;

  private SimpleUser authenticatedUser;

  @BeforeEach
  public void setUp() throws IllegalAccessException {
    authenticatedUser = simpleUser();
  }

  @Test
  public void createBoard_blankPayload_shouldFailAndReturn400() throws Exception {
    mvc.perform(post("/api/boards")
        .with(user(authenticatedUser)))
        .andExpect(status().is(400));
  }

  @Test
  public void createBoard_validPayload_shouldSucceedAndReturn201() throws Exception {

    CreateBoardPayload mockPayload = Mockito.mock(CreateBoardPayload.class);
    when(mockPayload.getName()).thenReturn("Test Board");
    when(mockPayload.getDescription()).thenReturn("Test Description");
    when(mockPayload.getTeamId()).thenReturn(1L);

    Board mockBoard = Mockito.mock(Board.class);
    when(mockBoard.getId()).thenReturn(new BoardId(1L));
    when(mockBoard.getUserId()).thenReturn(new UserId(1L));
    when(mockBoard.getName()).thenReturn("Test Board");
    when(mockBoard.getDescription()).thenReturn("Test Description");
    when(mockBoard.getTeamId()).thenReturn(new TeamId(1L));

    when(boardService.createBoard(any(CreateBoardCommand.class))).thenReturn(mockBoard);

    mvc.perform(
        post("/api/boards")
            .with(user(authenticatedUser))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(mockPayload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.teamId").value(1))
        .andExpect(jsonPath("$.name").value("Test Board"))
        .andExpect(jsonPath("$.description").value("Test Description"))
        .andExpect(jsonPath("$.id").value(1));
  }

  private SimpleUser simpleUser() throws IllegalAccessException {
    User user = User.create("sunny", "sunny@agilevolve.com", "MyPassword@");
    FieldUtils.writeField(user, "id", 1L, true);

    return new SimpleUser(user);
  }

}
