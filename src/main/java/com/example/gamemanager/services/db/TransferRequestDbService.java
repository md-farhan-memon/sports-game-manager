package com.example.gamemanager.services.db;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.gamemanager.commons.TransferRequestStatus;
import com.example.gamemanager.models.Team;
import com.example.gamemanager.models.TeamPlayer;
import com.example.gamemanager.models.TransferHistory;
import com.example.gamemanager.models.TransferRequest;
import com.example.gamemanager.models.User;
import com.example.gamemanager.repositories.TeamPlayerRepository;
import com.example.gamemanager.repositories.TeamRepository;
import com.example.gamemanager.repositories.TransferHistoryRepository;
import com.example.gamemanager.repositories.TransferRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransferRequestDbService {

  @Autowired
  private TransferRequestRepository transferRequestRepository;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private TeamPlayerRepository teamPlayerRepository;

  @Autowired
  private TransferHistoryRepository transferHistoryRepository;

  @Transactional
  public Page<TransferRequest> findAllPendingPaginated(Pageable pageable) {
    return transferRequestRepository.findAllByStatus(pageable, TransferRequestStatus.Pending);
  }

  @Transactional
  public TransferRequest save(TransferRequest transferRequest) {
    return transferRequestRepository.save(transferRequest);
  }

  @Transactional
  public TransferRequest findByPlayerAndRequesterAndStatus(TeamPlayer teamPlayer, User requester,
      TransferRequestStatus status) {
    return transferRequestRepository.findByTeamPlayer_IdAndRequester_IdAndStatus(teamPlayer.getId(), requester.getId(),
        status);
  }

  @Transactional
  public Optional<TransferRequest> findByIdAndPending(Long id) {
    return transferRequestRepository.findByIdAndStatus(id, TransferRequestStatus.Pending);
  }

  @Transactional
  public void persistTransfer(Team oldTeam, Team newTeam, TeamPlayer player, TransferRequest transferRequest,
      TransferHistory transferHistory) {
    teamRepository.save(oldTeam);
    teamRepository.save(newTeam);
    teamPlayerRepository.save(player);
    transferRequestRepository.save(transferRequest);
    transferHistoryRepository.save(transferHistory);
  }
}
