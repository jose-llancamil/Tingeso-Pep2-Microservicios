package com.autofix.msrepairs.services;

import com.autofix.msrepairs.clients.VehicleFeignClient;
import com.autofix.msrepairs.entities.RepairDetailsEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.RepairDetailsRepository;
import com.autofix.msrepairs.repositories.RepairRepository;
import com.autofix.msrepairs.requests.RepairDetailDTO;
import com.autofix.msrepairs.requests.VehicleDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;
    private final RepairDetailsRepository repairDetailsRepository;
    private VehicleFeignClient vehicleFeignClient;

    // Repair CRUD operations
    public List<RepairEntity> findAllRepairs() {
        return repairRepository.findAll();
    }

    public Optional<RepairEntity> findRepairById(Long id) {
        return repairRepository.findById(id);
    }

    public RepairEntity saveRepair(@Valid RepairEntity repair) {
        return repairRepository.save(repair);
    }

    public RepairEntity updateRepair(Long id, @Valid RepairEntity repair) {
        if (!repairRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair not found with id " + id);
        }
        repair.setId(id);
        return repairRepository.save(repair);
    }

    public void deleteRepairById(Long id) {
        if (!repairRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair not found with id " + id);
        }
        repairRepository.deleteById(id);
    }

    // RepairDetails CRUD operations
    public List<RepairDetailsEntity> findAllRepairDetails() {
        return repairDetailsRepository.findAll();
    }

    public Optional<RepairDetailsEntity> findRepairDetailsById(Long id) {
        return repairDetailsRepository.findById(id);
    }

    public List<RepairDetailDTO> getRepairDetailsByVehicleId(Long vehicleId) {
        VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found");
        }

        List<RepairEntity> repairs = repairRepository.findAllByVehiculoId(vehicleId);

        return repairs.stream()
                .flatMap(repair -> {
                    List<RepairDetailsEntity> repairDetails = repairDetailsRepository.findAllByRepairId(repair.getId());
                    return repairDetails.stream().map(detail -> {
                        RepairDetailDTO dto = new RepairDetailDTO();
                        dto.setId(detail.getId());
                        dto.setRepairId(detail.getRepairId());
                        dto.setTipoReparacion(detail.getRepairType());
                        dto.setFechaReparacion(detail.getRepairDate());
                        dto.setHoraReparacion(detail.getRepairTime());
                        dto.setMontoReparacion(detail.getRepairAmount());
                        dto.setPatenteVehiculo(vehicle.getPatente());
                        return dto;
                    });
                })
                .collect(Collectors.toList());
    }

    public RepairDetailsEntity saveRepairDetails(@Valid RepairDetailsEntity repairDetails) {
        return repairDetailsRepository.save(repairDetails);
    }

    public RepairDetailsEntity updateRepairDetails(Long id, @Valid RepairDetailsEntity repairDetails) {
        if (!repairDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair details not found with id " + id);
        }
        repairDetails.setId(id);
        return repairDetailsRepository.save(repairDetails);
    }

    public void deleteRepairDetailsById(Long id) {
        if (!repairDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair details not found with id " + id);
        }
        repairDetailsRepository.deleteById(id);
    }
}