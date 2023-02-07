package com.project.planit.voteItem.entity;

import com.project.planit.util.BaseEntity;
import com.project.planit.util.BaseRequest;
import com.project.planit.vote.entity.Vote;
<<<<<<< HEAD
import com.project.planit.voteItem.dto.FindVoteItemResponse;
=======
import javax.validation.constraints.NotBlank;
>>>>>>> 238216d15b44b9c3433b2f1723ab4d2689c983b1

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * packageName    : com.project.planit.voteItem.entity
 * fileName       : VoteItem
 * author         : Gukss
 * date           : 2023-01-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-01-23        Gukss       최초 생성
 */

@Entity
@Getter
@Table(name="vote_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VoteItem extends BaseEntity{

    @Id
    @Column(name="vote_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull
    private Long id;

    @Column(name="vote_item_name")
    @NotNull
    private  String voteItemName;

    @Embedded
    @NotNull
    private BaseRequest baseRequest;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="vote_id")
    @NotNull
    private Vote vote;

    public void changeVoteItemName(String newName){
        //바꾼 이름이 비어있을 때 untitle로 변경
        if(newName.equals("")){
            newName = "untitle";
        }
        this.voteItemName = newName;
    }
<<<<<<< HEAD

    public static VoteItem create(String voteItemName, BaseRequest baseRequest, Vote vote){
        return VoteItem.builder()
            .voteItemName(voteItemName)
            .baseRequest(baseRequest)
            .vote(vote)
            .build();
    }

    public FindVoteItemResponse createFindVoteItemResponse(){
        return FindVoteItemResponse.builder()
            .voteItemId(this.id)
            .voteId(this.vote.getId())
            .voteItemName(this.voteItemName)
            .build();
    }
=======
>>>>>>> 238216d15b44b9c3433b2f1723ab4d2689c983b1
}
