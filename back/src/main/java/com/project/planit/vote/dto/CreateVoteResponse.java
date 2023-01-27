package com.project.planit.vote.dto;

import lombok.Data;

/**
 * packageName    : com.project.planit.vote.dto fileName       : CreateVoteResponse author         :
 * SSAFY date           : 2023-01-26 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-01-26        SSAFY       최초
 * 생성
 */
@Data
public class CreateVoteResponse {
  private Long id;

  public CreateVoteResponse(Long id) {
    this.id = id;
  }
}