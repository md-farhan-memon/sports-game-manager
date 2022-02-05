package com.example.gamemanager.repositories;

import com.example.gamemanager.models.TransferHistory;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransferHistoryRepository extends PagingAndSortingRepository<TransferHistory, Long> {
}
