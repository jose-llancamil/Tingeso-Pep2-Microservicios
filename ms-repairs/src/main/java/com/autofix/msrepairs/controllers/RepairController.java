package com.autofix.msrepairs.controllers;

import com.autofix.msrepairs.entities.RepairDetailsEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.requests.RepairDetailDTO;
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

    // Repair CRUD endpoints
    @GetMapping
    public List<RepairEntity> getAllRepairs() {
        return repairService.findAllRepairs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairEntity> getRepairById(@PathVariable Long id) {
        return repairService.findRepairById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RepairEntity> createRepair(@RequestBody @Valid RepairEntity repair) {
        RepairEntity createdRepair = repairService.saveRepair(repair);
        return ResponseEntity.ok(createdRepair);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepairEntity> updateRepair(@PathVariable Long id, @RequestBody @Valid RepairEntity repair) {
        try {
            RepairEntity updatedRepair = repairService.updateRepair(id, repair);
            return ResponseEntity.ok(updatedRepair);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable Long id) {
        try {
            repairService.deleteRepairById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // RepairDetails CRUD endpoints
    @GetMapping("/details")
    public List<RepairDetailsEntity> getAllRepairDetails() {
        return repairService.findAllRepairDetails();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<RepairDetailsEntity> getRepairDetailsById(@PathVariable Long id) {
        return repairService.findRepairDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/details")
    public ResponseEntity<RepairDetailsEntity> createRepairDetails(@RequestBody @Valid RepairDetailsEntity repairDetails) {
        RepairDetailsEntity createdRepairDetails = repairService.saveRepairDetails(repairDetails);
        return ResponseEntity.ok(createdRepairDetails);
    }

    @GetMapping("/details/{vehicleId}")
    public List<RepairDetailDTO> getRepairDetailsByVehicleId(@PathVariable Long vehicleId) {
        return repairService.getRepairDetailsByVehicleId(vehicleId);
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<RepairDetailsEntity> updateRepairDetails(@PathVariable Long id, @RequestBody @Valid RepairDetailsEntity repairDetails) {
        try {
            RepairDetailsEntity updatedRepairDetails = repairService.updateRepairDetails(id, repairDetails);
            return ResponseEntity.ok(updatedRepairDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<Void> deleteRepairDetails(@PathVariable Long id) {
        try {
            repairService.deleteRepairDetailsById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

