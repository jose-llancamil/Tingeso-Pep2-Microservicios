package com.autofix.msrepairs.controllers;

import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.services.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/repairs")
@RequiredArgsConstructor
@Validated
public class RepairController {

    private final RepairService repairService;

    /**
     * Retrieves all repairs.
     * @return a list of all repairs.
     */
    @GetMapping
    public List<RepairEntity> getAllRepairs() {
        return repairService.findAllRepairs();
    }

    /**
     * Retrieves a repair by its ID.
     * @param id the ID of the repair.
     * @return the repair entity if found, otherwise a 404 response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepairEntity> getRepairById(@PathVariable Long id) {
        return repairService.findRepairById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new repair.
     * @param repair the repair entity to be created.
     * @return the created repair entity.
     */
    @PostMapping
    public ResponseEntity<RepairEntity> createRepair(@RequestBody @Valid RepairEntity repair) {
        RepairEntity createdRepair = repairService.saveRepair(repair);
        return ResponseEntity.ok(createdRepair);
    }

    /**
     * Updates an existing repair by its ID.
     * @param id the ID of the repair to be updated.
     * @param repair the updated repair entity.
     * @return the updated repair entity if found, otherwise a 404 response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepairEntity> updateRepair(@PathVariable Long id, @RequestBody @Valid RepairEntity repair) {
        try {
            RepairEntity updatedRepair = repairService.updateRepair(id, repair);
            return ResponseEntity.ok(updatedRepair);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a repair by its ID.
     * @param id the ID of the repair to be deleted.
     * @return a 204 response if successful, otherwise a 404 response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable Long id) {
        try {
            repairService.deleteRepairById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}