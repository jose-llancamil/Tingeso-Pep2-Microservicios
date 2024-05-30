package com.autofix.msrepairs.services;

import com.autofix.msrepairs.clients.VehicleFeignClient;
import com.autofix.msrepairs.requests.RepairTypeReportDTO;
import com.autofix.msrepairs.requests.MonthlyRepairComparisonDTO;
import com.autofix.msrepairs.entities.RepairDetailsEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.RepairDetailsRepository;
import com.autofix.msrepairs.repositories.RepairRepository;
import com.autofix.msrepairs.requests.VehicleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final RepairRepository repairRepository;
    private final RepairDetailsRepository repairDetailsRepository;
    private final VehicleFeignClient vehicleFeignClient;

    public List<RepairTypeReportDTO> generateRepairTypeReport(int month, int year) {
        List<RepairEntity> repairs = repairRepository.findAllByMonthAndYear(month, year);

        Map<String, RepairTypeReportDTO> reportMap = repairs.stream()
                .flatMap(repair -> repairDetailsRepository.findAllByRepairId(repair.getId()).stream())
                .collect(Collectors.groupingBy(
                        RepairDetailsEntity::getRepairType,
                        Collectors.collectingAndThen(Collectors.toList(), repairDetailsList -> {
                            RepairTypeReportDTO dto = new RepairTypeReportDTO();
                            dto.setRepairType(repairDetailsList.get(0).getRepairType());
                            dto.setSedanCount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("sedan");
                            }).count());
                            dto.setHatchbackCount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("hatchback");
                            }).count());
                            dto.setSuvCount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("suv");
                            }).count());
                            dto.setPickupCount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("pickup");
                            }).count());
                            dto.setVanCount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("furgoneta");
                            }).count());
                            dto.setTotalAmount(repairDetailsList.stream().mapToDouble(rd -> rd.getRepairAmount().doubleValue()).sum());
                            return dto;
                        })
                ));

        return reportMap.values().stream().collect(Collectors.toList());
    }

    public List<MonthlyRepairComparisonDTO> generateMonthlyRepairComparisonReport(int month, int year) {
        LocalDate date = LocalDate.of(year, month, 1);

        List<MonthlyRepairComparisonDTO> report = List.of(
                getMonthlyRepairData(date.minusMonths(2)),
                getMonthlyRepairData(date.minusMonths(1)),
                getMonthlyRepairData(date)
        );

        return report;
    }

    private MonthlyRepairComparisonDTO getMonthlyRepairData(LocalDate date) {
        List<RepairEntity> repairs = repairRepository.findAllByMonthAndYear(date.getMonthValue(), date.getYear());

        MonthlyRepairComparisonDTO dto = new MonthlyRepairComparisonDTO();
        dto.setMonth(date.getMonth().name());
        dto.setRepairCount(repairs.size());
        dto.setTotalAmount(repairs.stream().mapToDouble(repair -> repair.getTotalCost().doubleValue()).sum());

        return dto;
    }
}
