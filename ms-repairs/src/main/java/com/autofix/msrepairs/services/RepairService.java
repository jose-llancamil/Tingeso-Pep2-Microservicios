package com.autofix.msrepairs.services;

import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.RepairRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;

    /**
     * Retrieves all repairs.
     * @return a list of all repairs.
     */
    public List<RepairEntity> findAllRepairs() {
        return repairRepository.findAll();
    }

    /**
     * Retrieves a repair by its ID.
     * @param id the ID of the repair.
     * @return an optional containing the repair if found, or empty if not.
     */
    public Optional<RepairEntity> findRepairById(Long id) {
        return repairRepository.findById(id);
    }

    /**
     * Saves a new repair.
     * @param repair the repair entity to be saved.
     * @return the saved repair entity.
     */
    public RepairEntity saveRepair(@Valid RepairEntity repair) {
        return repairRepository.save(repair);
    }

    /**
     * Updates an existing repair by its ID.
     * @param id the ID of the repair to be updated.
     * @param repair the updated repair entity.
     * @return the updated repair entity.
     */
    public RepairEntity updateRepair(Long id, @Valid RepairEntity repair) {
        if (!repairRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair not found with id " + id);
        }
        repair.setId(id);
        return repairRepository.save(repair);
    }

    /**
     * Deletes a repair by its ID.
     * @param id the ID of the repair to be deleted.
     */
    public void deleteRepairById(Long id) {
        if (!repairRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair not found with id " + id);
        }
        repairRepository.deleteById(id);
    }
}