package com.example.srsBrokerage.Repository;

import com.example.srsBrokerage.Model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
