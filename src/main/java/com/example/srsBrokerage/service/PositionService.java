package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.response.position.PositionResponse;
import com.example.srsBrokerage.exceptions.PositionNotFoundException;
import com.example.srsBrokerage.mapper.PositionMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.Asset;
import com.example.srsBrokerage.model.Position;
import com.example.srsBrokerage.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    PositionRepository positionRepository;
    PositionMapper positionMapper;

    public PositionService(
            PositionRepository positionRepository,
            PositionMapper positionMapper
    ) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
    }

    public List<PositionResponse> getPositionsForAccount(Long accountId) {
        List<Position> positions = positionRepository.findByAccountId(accountId);
        return positionMapper.toDtoList(positions);
    }

    public PositionResponse getPosition(Long accountId, Long assetId) {
        Position position = positionRepository.findByAccountAndAsset(accountId, assetId)
                .orElseThrow(() -> new PositionNotFoundException("Position not found."));

        return positionMapper.toDto(position);
    }
}
