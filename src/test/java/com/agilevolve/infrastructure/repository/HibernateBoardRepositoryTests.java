package com.agilevolve.infrastructure.repository;

import java.util.Collections;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ActiveProfiles;

import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.board.BoardMember;
import com.agilevolve.domain.model.board.BoardMemberRepository;
import com.agilevolve.domain.model.board.BoardRepository;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.UserId;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 보드 관련 인프라 서비스 테스트
 *
 * 테스트 메소드는 [작업 단위_테스트 중인 상태_ 예상되는 행동] 명명 규약을 따른다.
 */
@ActiveProfiles("test")
@DataJpaTest
public class HibernateBoardRepositoryTests {

  @TestConfiguration
  public static class BoardRepositoryTestContextConfiguration {
    @Bean
    BoardRepository boardRepository(EntityManager entityManager) {
      return new HibernateBoardRepository(entityManager);
    }

    @Bean
    BoardMemberRepository boardMemberRepository(EntityManager entityManager) {
      return new HibernateBoardMemberRepository(entityManager);
    }
  }

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private BoardMemberRepository boardMemberRepository;

  @Test
  public void save_invalidBoard_shouldFail() {
    // Arrange
    UserId creatorId = new UserId(11L);
    String name = null;
    String description = "New Board!!";
    TeamId teamId = new TeamId(10L);

    Board invalidBoard = Board.create(creatorId, name, description, teamId);

    // ACt & Assert
    assertThrows(PersistenceException.class, () -> boardRepository.save(invalidBoard));
  }

  @Test
  public void save_validBoard_shouldSuccess() {
    // Arrange
    UserId creatorId = new UserId(11L);
    String name = "New Board";
    String description = "New Board!!";
    TeamId teamId = new TeamId(10L);

    Board validBoard = Board.create(creatorId, name, description, teamId);

    // ACt
    boardRepository.save(validBoard);

    // Assert
    assertNotNull(validBoard.getId(), "새로운 보드의 아이디가 생성되어야 합니다.");
    assertNotNull(validBoard.getCreatedDate(), "새로운 보드의 생성일이 생성되어야 합니다.");
    assertEquals(name, validBoard.getName());
    assertEquals(description, validBoard.getDescription());
  }

  @Test
  public void findBoardsByMembership_emptyMembership_shouldReturnEmptyResult() {
    // Arrange
    UserId creatorId = new UserId(11L);
    String name = "New Board";
    String description = "New Board!!";
    TeamId teamId = new TeamId(10L);

    Board newBoard = Board.create(creatorId, name, description, teamId);
    boardRepository.save(newBoard);

    // Act
    List<Board> found = boardRepository.findBoardsByMembership(creatorId);

    // Assert
    assertEquals(found, Collections.emptyList());
  }

  @Test
  public void findBoardsByMembership_vaildMembership_shouldReturnResult() {
    // Arrange
    UserId creatorId = new UserId(11L);
    String name = "New Board";
    String description = "New Board!!";
    TeamId teamId = new TeamId(10L);

    Board newBoard = Board.create(creatorId, name, description, teamId);
    boardRepository.save(newBoard);

    BoardMember newBoardMember = BoardMember.create(newBoard.getId(), creatorId);
    boardMemberRepository.save(newBoardMember);

    // Act
    List<Board> found = boardRepository.findBoardsByMembership(creatorId);

    // Assert
    assertNotNull(found, "보드 멤버에 속해있으면 보드를 찾아야 합니다.");
    assertEquals(name, found.get(0).getName());
  }

  @Test
  public void findById_emptyBoardId_shouldReturnEmptyResult() {
    // Arrange
    UserId creatorId = new UserId(11L);
    String name = "New Board";
    String description = "New Board!!";
    TeamId teamId = new TeamId(10L);

    BoardId emptyBoardId = new BoardId(999L);

    Board newBoard = Board.create(creatorId, name, description, teamId);
    boardRepository.save(newBoard);

    // Act
    Board found = boardRepository.findById(emptyBoardId);

    // Assert
    assertNull(found, "유효하지 않은 보드 id는 보드가 발견되지 않습니다.");
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
  public void findById_vaildBoardId_shouldReturnResult() {
    // Arrange
    UserId creatorId = new UserId(11L);
    String name = "New Board";
    String description = "New Board!!";
    TeamId teamId = new TeamId(10L);

    // auto_increment로 1부터 인덱스 생성
    BoardId vaildBoardId = new BoardId(1L);

    Board newBoard = Board.create(creatorId, name, description, teamId);
    boardRepository.save(newBoard);

    BoardMember newBoardMember = BoardMember.create(newBoard.getId(), creatorId);
    boardMemberRepository.save(newBoardMember);

    // Act
    Board found = boardRepository.findById(vaildBoardId);

    // Assert
    assertEquals(newBoard, found);
    assertEquals(name, found.getName());
  }
}
