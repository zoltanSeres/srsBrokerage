package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.response.position.PositionResponse;
import com.example.srsBrokerage.model.Position;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PositionMapper {

    public PositionResponse toDto(Position position) {

        return new PositionResponse(
                position.getId(),
                position.getAccount().getId(),
                position.getAssetId(),
                position.getHeldQuantity(),
                position.getAveragePrice(),
                position.getCreatedAt(),
                position.getUpdatedAt()
        );
    }

    public List<PositionResponse> toDtoList(List<Position> positions) {
        return positions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
