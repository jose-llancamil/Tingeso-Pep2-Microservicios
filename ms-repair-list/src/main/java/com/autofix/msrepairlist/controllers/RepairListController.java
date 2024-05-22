package com.autofix.msrepairlist.controllers;

import com.autofix.msrepairlist.entities.RepairListEntity;
import com.autofix.msrepairlist.services.RepairListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/repair-list")
public class RepairListController {

    @Autowired
    private RepairListService repairListService;

    @GetMapping
    public List<RepairListEntity> getAllRepairs() {
        return repairListService.getAllRepairs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairListEntity> getRepairById(@PathVariable Long id) {
        Optional<RepairListEntity> repair = repairListService.getRepairById(id);
        return repair.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RepairListEntity> createRepair(@RequestBody RepairListEntity repairListEntity) {
        RepairListEntity createdRepair = repairListService.createRepair(repairListEntity);
        return ResponseEntity.ok(createdRepair);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepairListEntity> updateRepair(@PathVariable Long id, @RequestBody RepairListEntity repairListEntity) {
        RepairListEntity updatedRepair = repairListService.updateRepair(id, repairListEntity);
        if (updatedRepair != null) {
            return ResponseEntity.ok(updatedRepair);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable Long id) {
        if (repairListService.deleteRepair(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}