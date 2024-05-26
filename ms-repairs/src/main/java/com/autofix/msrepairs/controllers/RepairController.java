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
import java.util.stream.Collectors;

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
    public ResponseEntity<RepairEntity> updateRepair(@PathVariable Long id, @RequestBody RepairEntity repair) {
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

    @GetMapping("/details/info/{id}")
    public ResponseEntity<RepairDetailsEntity> getRepairDetailsById(@PathVariable Long id) {
        return repairService.findRepairDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/details/{vehicleId}")
    public List<RepairDetailDTO> getRepairDetailsByVehicleId(@PathVariable Long vehicleId) {
        return repairService.getRepairDetailsByVehicleId(vehicleId);
    }

    @PostMapping("/details")
    public ResponseEntity<List<RepairDetailsEntity>> createRepairDetails(@RequestBody @Valid List<RepairDetailsEntity> repairDetailsList) {
        List<RepairDetailsEntity> createdRepairDetailsList = repairDetailsList.stream()
                .map(repairService::saveRepairDetails)
                .collect(Collectors.toList());
        return ResponseEntity.ok(createdRepairDetailsList);
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<RepairDetailsEntity> updateRepairDetails(@PathVariable Long id, @RequestBody RepairDetailsEntity repairDetails) {
        try {
            RepairDetailsEntity updatedRepairDetails = repairService.updateRepairDetails(id, repairDetails);
            return ResponseEntity.ok(updatedRepairDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<Void> deleteRepairDetailById(@PathVariable Long id) {
        try {
            repairService.deleteRepairDetailById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/details/vehicle/{vehicleId}")
    public ResponseEntity<Void> deleteRepairDetailsByVehicleId(@PathVariable Long vehicleId) {
        try {
            repairService.deleteRepairDetailsByVehicleId(vehicleId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

