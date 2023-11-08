package com.taskagile.web.results;

import org.springframework.http.ResponseEntity;

import com.taskagile.domain.model.board.Board;

public class CreateBoardResult {

  public static ResponseEntity<ApiResult> build(Board board) {
    ApiResult apiResult = ApiResult.blank()
        .add("id", board.getId().value())
        .add("name", board.getName())
        .add("description", board.getDescription())
        .add("teamId", board.getTeamId().value());
    return Result.ok(apiResult);
  }
}
