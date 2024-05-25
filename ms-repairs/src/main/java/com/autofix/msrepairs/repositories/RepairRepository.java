package com.autofix.msrepairs.repositories;

import com.autofix.msrepairs.entities.RepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<RepairEntity, Long> {
    List<RepairEntity> findAllByVehicleId(Long vehicleId);
}

