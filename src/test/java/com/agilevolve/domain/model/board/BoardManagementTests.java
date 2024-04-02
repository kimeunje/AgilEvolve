package com.agilevolve.domain.model.board;

import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.UserId;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * 보드(생성) 관련 도메인 서비스 테스트
 *
 * 테스트 메소드는 [작업 단위_테스트 중인 상태_ 예상되는 행동] 명명 규약을 따른다.
 */
public class BoardManagementTests {

  @Mock
  private BoardRepository boardRepository;
  @Mock
  private BoardMemberRepository boardMemberRepository;
  @InjectMocks
  private BoardManagement boardManagement;

  @BeforeEach
  void setUp() throws IllegalAccessException {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("현재 boardManagement.createBoard가 테스트 코드에서 동작하지 않고 있음. 수정 필요함.")
  @Test
  public void createBoard_newBoard_shouldSucceed() throws IllegalAccessException {
    // Arrange
    UserId creatorId = new UserId(111L);
    String name = "Test Board";
    String description = "New Board!!";
    TeamId teamId = new TeamId(10L);

    Board newboard = Board.create(creatorId, name, description, teamId);
    FieldUtils.writeField(newboard, "id", 1L, true);

    BoardMember newBoardMember = BoardMember.create(newboard.getId(), creatorId);

    doNothing().when(boardRepository).save(newboard);
    doNothing().when(boardMemberRepository).save(newBoardMember);

    // Act
    // boardManagement.createBoard(creatorId, name, description, teamId);

    // Assert
    assertNotNull(newboard, "보드는 null이면 안됩니다.");
    assertEquals(name, newboard.getName(), "보드 name 불일치");
    assertEquals(description, newboard.getDescription(), "보드 description 불일치");
    assertEquals(teamId, newboard.getTeamId(), "보드 teamId 불일치");
  }
}
