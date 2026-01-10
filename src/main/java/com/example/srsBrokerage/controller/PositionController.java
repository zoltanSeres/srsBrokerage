package com.example.srsBrokerage.controller;

import com.example.srsBrokerage.dto.response.position.PositionResponse;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<PositionResponse>> getPositionsForAccount(@PathVariable Long accountId) {
        List<PositionResponse> positions = positionService.getPositionsForAccount(accountId);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{accountId}/{assetId")
    public ResponseEntity<PositionResponse> getPosition(@PathVariable Long accountId, @PathVariable Long assetId) {
        PositionResponse positionResponse = positionService.getPosition(accountId, assetId);
        return ResponseEntity.ok(positionResponse);
    }
}
