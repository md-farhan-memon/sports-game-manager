package com.example.gamemanager.services.helper;

import java.util.Optional;

import com.example.gamemanager.commons.TransferRequestStatus;
import com.example.gamemanager.dtos.request.TransferRequestRequest;
import com.example.gamemanager.models.Team;
import com.example.gamemanager.models.TeamPlayer;
import com.example.gamemanager.models.TransferHistory;
import com.example.gamemanager.models.TransferRequest;
import com.example.gamemanager.models.User;
import com.example.gamemanager.services.db.TeamDbService;
import com.example.gamemanager.services.db.TransferRequestDbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferRequestService {

  @Autowired
  private TransferRequestDbService transferRequestDbService;

  @Autowired
  private TeamDbService teamDbService;

  public TransferRequest placeRequest(TransferRequestRequest transferRequestRequest, TeamPlayer teamPlayer,
      User requester) {
    TransferRequest existingTransferRequest = transferRequestDbService.findByPlayerAndRequesterAndStatus(teamPlayer,
        requester, TransferRequestStatus.Pending);
    if (existingTransferRequest == null) {
      TransferRequest transferRequest = new TransferRequest();
      transferRequest.setTeamPlayer(teamPlayer);
      transferRequest.setPrice(transferRequestRequest.getPrice());
      transferRequest.setRequester(requester);
      transferRequest.setStatus(TransferRequestStatus.Pending);

      return transferRequestDbService.save(transferRequest);
    } else {
      return existingTransferRequest;
    }
  }

  public boolean canBuy(TransferRequest transferRequest, Team team) {
    return (transferRequest.getPrice() <= team.getWallet() && transferRequest.getRequester() != team.getUser());
  }

  public TransferHistory completeTransfer(TransferRequest transferRequest, Team newTeam) {
    // Pre Transfer
    TeamPlayer player = transferRequest.getTeamPlayer();
    Team oldTeam = player.getTeam();

    oldTeam.setWallet(oldTeam.getWallet() + transferRequest.getPrice());

    newTeam.setWallet(newTeam.getWallet() - transferRequest.getPrice());

    player.setTeam(newTeam);
    player.setValue(player.improvedValueAfterTranser());

    TransferHistory transferHistory = new TransferHistory();
    transferHistory.setTeamPlayer(player);
    transferHistory.setTransferRequest(transferRequest);
    transferHistory.setOldTeam(oldTeam);
    transferHistory.setNewTeam(newTeam);

    transferRequest.setBuyer(newTeam.getUser());
    transferRequest.setStatus(TransferRequestStatus.Completed);

    transferRequestDbService.persistTransfer(oldTeam, newTeam, player, transferRequest, transferHistory);

    // Post Transfer
    Optional<Team> oldTeamOptional = teamDbService.findById(oldTeam.getId());
    Optional<Team> newTeamOptional = teamDbService.findById(newTeam.getId());

    if (oldTeamOptional.isPresent()) {
      oldTeam = oldTeamOptional.get();
      oldTeam.setValue(oldTeam.calculateTeamValue());
      teamDbService.save(oldTeam);
    }

    if (newTeamOptional.isPresent()) {
      newTeam = newTeamOptional.get();
      newTeam.setValue(newTeam.calculateTeamValue());
      teamDbService.save(newTeam);
    }

    return transferHistory;
  }
}
