package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
