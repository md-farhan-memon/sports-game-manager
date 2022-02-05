package com.example.gamemanager.repositories;

import java.util.Optional;

import com.example.gamemanager.commons.TransferRequestStatus;
import com.example.gamemanager.models.TransferRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransferRequestRepository extends PagingAndSortingRepository<TransferRequest, Long> {

  Page<TransferRequest> findAll(Pageable pageable);

  Page<TransferRequest> findAllByStatus(Pageable pageable, TransferRequestStatus pending);

  TransferRequest findByTeamPlayer_IdAndRequester_IdAndStatus(Long teamPlayerId, Long requesterId,
      TransferRequestStatus status);

  Optional<TransferRequest> findByIdAndStatus(Long id, TransferRequestStatus status);
}
