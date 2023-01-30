package com.project.planit.vote.controller;

import com.project.planit.common.exception.NotFoundException;
import com.project.planit.room.entity.Room;
import com.project.planit.room.repository.RoomRepository;
import com.project.planit.room.service.RoomServiceImpl;
import com.project.planit.vote.dto.CreateVoteRequest;
import com.project.planit.vote.dto.CreateVoteResponse;
import com.project.planit.vote.dto.FindVoteByRoomIdResponse;
import com.project.planit.vote.entity.Vote;
import com.project.planit.vote.service.VoteServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.project.planit.vote.controller fileName       : VoteController author
 *  : Gukss date           : 2023-01-25 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-01-25        Gukss       최초
 * 생성
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/votes")
public class VoteController {
  private final RoomRepository roomRepository;
  private final VoteServiceImpl voteService;
  private final RoomServiceImpl roomService;

  @PostMapping
  public ResponseEntity<CreateVoteResponse> createVote(@RequestBody CreateVoteRequest request){
//    log.info("vote controller");
    Vote createdVote = voteService.createVote(request);
    CreateVoteResponse createVoteResponse = CreateVoteResponse.create(createdVote);
    return ResponseEntity.ok()
//        .header()
        .body(createVoteResponse);
  }

  @GetMapping(path = "{roomId}")
  public ResponseEntity<FindVoteByRoomIdResponse> findVoteByRoomId(@PathVariable Long roomId){
    //fetch.lazy 때문에 room이 바로 초기화되지 않고 정보가 채워지는 프록시 객체로 채워진다.
    //room의 초기화가 안되어있기 때문에 room으로 voteList를 찾아올 수 없다.
    Room room = roomRepository.findById(roomId).orElseThrow(()->new NotFoundException(NotFoundException.VOTE_NOT_FOUND));
    List<Vote> foundVotes = voteService.findByRoom(room);


    return ResponseEntity.ok()
        .body(FindVoteByRoomIdResponse.create(foundVotes));
  }
}
