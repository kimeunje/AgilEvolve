package com.agilevolve.web.apis;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.agilevolve.config.SecurityConfiguration;
import com.agilevolve.domain.application.CardListService;
import com.agilevolve.domain.application.commands.AddCardListCommand;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.cardlist.CardListId;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;
import com.agilevolve.utils.JsonUtils;
import com.agilevolve.web.payload.AddCardListPayload;
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
@ContextConfiguration(classes = { SecurityConfiguration.class, CardListApiController.class })
@WebMvcTest
public class CardListApiControllerTests {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private CardListService cardListService;

  private SimpleUser authenticatedUser;

  @BeforeEach
  public void setUp() throws IllegalAccessException {
    authenticatedUser = simpleUser();
  }

  @Test
  public void addCardList_blankPayload_shouldFailAndReturn400() throws Exception {
    mvc.perform(post("/api/card-lists")
        .with(user(authenticatedUser)))
        .andExpect(status().is(400));
  }

  @Test
  public void addCardList_validPayload_shouldSucceedAndReturn201() throws Exception {

    AddCardListPayload mockPayload = Mockito.mock(AddCardListPayload.class);
    when(mockPayload.getBoardId()).thenReturn(1L);
    when(mockPayload.getName()).thenReturn("Test CardList");
    when(mockPayload.getPosition()).thenReturn(1);

    CardList mockCardList = Mockito.mock(CardList.class);
    when(mockCardList.getId()).thenReturn(new CardListId(1L));
    when(mockCardList.getUserId()).thenReturn(new UserId(1L));
    when(mockCardList.getName()).thenReturn("Test Board");
    when(mockCardList.getPosition()).thenReturn(1);
    when(mockCardList.isArchived()).thenReturn(false);
    when(mockCardList.getCreatedDate()).thenReturn(new Date());

    when(cardListService.addCardList(any(AddCardListCommand.class))).thenReturn(mockCardList);

    mvc.perform(
        post("/api/card-lists")
            .with(user(authenticatedUser))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(mockPayload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Test Board"));
  }

  private SimpleUser simpleUser() throws IllegalAccessException {
    User user = User.create("sunny", "sunny@agilevolve.com", "MyPassword@");
    FieldUtils.writeField(user, "id", 1L, true);

    return new SimpleUser(user);
  }
}
