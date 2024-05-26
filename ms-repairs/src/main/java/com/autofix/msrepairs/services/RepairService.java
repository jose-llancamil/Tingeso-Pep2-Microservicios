package com.autofix.msrepairs.services;

import com.autofix.msrepairs.clients.RepairListFeignClient;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;
    private final RepairDetailsRepository repairDetailsRepository;
    private final VehicleFeignClient vehicleFeignClient;
    private final RepairListFeignClient repairListFeignClient;

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

        List<RepairEntity> repairs = repairRepository.findAllByVehicleId(vehicleId);

        return repairs.stream()
                .flatMap(repair -> {
                    List<RepairDetailsEntity> repairDetails = repairDetailsRepository.findAllByRepairId(repair.getId());
                    return repairDetails.stream().map(detail -> {
                        RepairDetailDTO dto = new RepairDetailDTO();
                        dto.setId(detail.getId());
                        dto.setRepairId(detail.getRepairId());
                        dto.setRepairType(detail.getRepairType());
                        dto.setRepairDate(detail.getRepairDate());
                        dto.setRepairTime(detail.getRepairTime());
                        dto.setRepairAmount(detail.getRepairAmount());
                        dto.setLicensePlate(vehicle.getLicensePlate());
                        return dto;
                    });
                })
                .collect(Collectors.toList());
    }

    public RepairDetailsEntity saveRepairDetails(RepairDetailsEntity repairDetailsEntity) {
        // Obtener la reparación para obtener el vehicleId
        Optional<RepairEntity> repairOpt = repairRepository.findById(repairDetailsEntity.getRepairId());
        if (repairOpt.isPresent()) {
            Long vehicleId = repairOpt.get().getVehicleId();
            VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);

            // Obtener el precio de la reparación desde ms-repair-list
            BigDecimal repairPrice = repairListFeignClient.getRepairPrice(
                    repairDetailsEntity.getRepairType(), vehicle.getEngineType()
            );
            repairDetailsEntity.setRepairAmount(repairPrice);

            // Guardar el detalle de la reparación
            RepairDetailsEntity savedDetail = repairDetailsRepository.save(repairDetailsEntity);

            // Actualizar los montos de la reparación principal
            updateRepairAmounts(repairDetailsEntity.getRepairId());

            return savedDetail;
        } else {
            throw new RuntimeException("Repair not found");
        }
    }

    private void updateRepairAmounts(Long repairId) {
        List<RepairDetailsEntity> details = repairDetailsRepository.findAllByRepairId(repairId);

        double totalRepairAmount = details.stream().mapToDouble(d -> d.getRepairAmount().doubleValue()).sum();
        double surchargeAmount = calculateSurcharge(totalRepairAmount);
        double discountAmount = calculateDiscount(totalRepairAmount);
        double taxAmount = totalRepairAmount * 0.19;
        double totalCost = totalRepairAmount + surchargeAmount - discountAmount + taxAmount;

        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        repair.setTotalRepairAmount(totalRepairAmount);
        repair.setSurchargeAmount(surchargeAmount);
        repair.setDiscountAmount(discountAmount);
        repair.setTaxAmount(taxAmount);
        repair.setTotalCost(totalCost);

        repairRepository.save(repair);
    }

    private double calculateSurcharge(double amount) {
        // Lógica para calcular los recargos
        return amount * 0.05;
    }

    private double calculateDiscount(double amount) {
        // Lógica para calcular los descuentos
        return amount * 0.1;
    }
    public RepairDetailsEntity updateRepairDetails(Long id, @Valid RepairDetailsEntity repairDetails) {
        if (!repairDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair details not found with id " + id);
        }
        repairDetails.setId(id);
        return repairDetailsRepository.save(repairDetails);
    }

    public void deleteRepairDetailById(Long id) {
        if (!repairDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair detail not found with id " + id);
        }
        repairDetailsRepository.deleteById(id);
    }

    public void deleteRepairDetailsByVehicleId(Long vehicleId) {
        List<RepairEntity> repairs = repairRepository.findAllByVehicleId(vehicleId);
        if (repairs.isEmpty()) {
            throw new IllegalArgumentException("No repairs found for vehicleId " + vehicleId);
        }

        repairs.forEach(repair -> {
            List<RepairDetailsEntity> repairDetails = repairDetailsRepository.findAllByRepairId(repair.getId());
            repairDetailsRepository.deleteAll(repairDetails);
            repairRepository.delete(repair);
        });
    }
}