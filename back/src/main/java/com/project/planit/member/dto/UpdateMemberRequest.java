package com.project.planit.member.dto;

import javax.validation.constraints.NotNull;

import com.project.planit.util.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.planit.member.dto fileName       : updateMemberRequest author
 *  : SSAFY date           : 2023-01-28 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-01-28        SSAFY       최초
 * 생성
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMemberRequest {
  @NotNull
  private String memberAppId;
  @NotNull
  private String memberAppPwd;
  @NotNull
  private String memberName;
  @NotNull
  private String memberEmail;

<<<<<<< HEAD
=======
  @NotNull
  private BaseRequest baseRequest;
>>>>>>> 238216d15b44b9c3433b2f1723ab4d2689c983b1
}
