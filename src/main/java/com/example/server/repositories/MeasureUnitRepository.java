package com.example.server.repositories;

import com.example.server.entities.MeasureUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Long> {
} 