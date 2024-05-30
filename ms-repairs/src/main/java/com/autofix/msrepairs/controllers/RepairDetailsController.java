package com.autofix.msrepairs.controllers;

import com.autofix.msrepairs.entities.RepairDetailsEntity;
import com.autofix.msrepairs.requests.RepairDetailDTO;
import com.autofix.msrepairs.services.RepairDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repair-details")
@RequiredArgsConstructor
@Validated
public class RepairDetailsController {

    private final RepairDetailsService repairDetailsService;

    /**
     * Retrieves all repair details.
     * @return a list of all repair details.
     */
    @GetMapping
    public List<RepairDetailsEntity> getAllRepairDetails() {
        return repairDetailsService.findAllRepairDetails();
    }

    /**
     * Retrieves repair details by its ID.
     * @param id the ID of the repair details.
     * @return the repair details entity if found, otherwise a 404 response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepairDetailsEntity> getRepairDetailsById(@PathVariable Long id) {
        return repairDetailsService.findRepairDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves repair details by vehicle ID.
     * @param vehicleId the ID of the vehicle.
     * @return a list of repair details for the specified vehicle.
     */
    @GetMapping("/vehicle/{vehicleId}")
    public List<RepairDetailDTO> getRepairDetailsByVehicleId(@PathVariable Long vehicleId) {
        return repairDetailsService.getRepairDetailsByVehicleId(vehicleId);
    }

    /**
     * Creates new repair details.
     * @param repairDetailsList the list of repair details entities to be created.
     * @return the created repair details entities.
     */
    @PostMapping
    public ResponseEntity<List<RepairDetailsEntity>> createRepairDetails(@RequestBody @Valid List<RepairDetailsEntity> repairDetailsList) {
        List<RepairDetailsEntity> createdRepairDetailsList = repairDetailsList.stream()
                .map(repairDetailsService::saveRepairDetails)
                .collect(Collectors.toList());
        return ResponseEntity.ok(createdRepairDetailsList);
    }

    /**
     * Updates an existing repair details by its ID.
     * @param id the ID of the repair details to be updated.
     * @param repairDetails the updated repair details entity.
     * @return the updated repair details entity if found, otherwise a 404 response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepairDetailsEntity> updateRepairDetails(@PathVariable Long id, @RequestBody @Valid RepairDetailsEntity repairDetails) {
        try {
            RepairDetailsEntity updatedRepairDetails = repairDetailsService.updateRepairDetails(id, repairDetails);
            return ResponseEntity.ok(updatedRepairDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a repair detail by its ID.
     * @param id the ID of the repair detail to be deleted.
     * @return a 204 response if successful, otherwise a 404 response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepairDetailById(@PathVariable Long id) {
        try {
            repairDetailsService.deleteRepairDetailById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes all repair details and associated repairs for a specific vehicle ID.
     * @param vehicleId the ID of the vehicle.
     * @return a 204 response if successful, otherwise a 404 response.
     */
    @DeleteMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Void> deleteRepairDetailsByVehicleId(@PathVariable Long vehicleId) {
        try {
            repairDetailsService.deleteRepairDetailsByVehicleId(vehicleId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}