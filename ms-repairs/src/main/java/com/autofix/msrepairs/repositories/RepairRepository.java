package com.autofix.msrepairs.repositories;

import com.autofix.msrepairs.entities.RepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<RepairEntity, Long> {
    List<RepairEntity> findAllByVehicleId(Long vehicleId);
    List<RepairEntity> findAllByVehicleIdAndEntryDateAfter(Long vehicleId, LocalDate entryDate);
}

