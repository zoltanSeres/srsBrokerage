package com.example.srsBrokerage.controller;

import com.example.srsBrokerage.dto.response.position.PositionResponse;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/accounts/{accountId}/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getPositionsForAccount(@PathVariable Long accountId) {
        List<PositionResponse> positions = positionService.getPositionsForAccount(accountId);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{positionId}")
    public ResponseEntity<PositionResponse> getPosition(
            @PathVariable Long userId,
            @PathVariable Long accountId,
            @PathVariable Long positionId,
            @RequestParam Long assetId
    ) {
        PositionResponse positionResponse = positionService.getPosition(accountId, assetId);
        return ResponseEntity.ok(positionResponse);
    }
}
