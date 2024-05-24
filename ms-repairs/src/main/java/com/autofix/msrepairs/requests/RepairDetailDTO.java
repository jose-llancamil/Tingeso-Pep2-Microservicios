package com.autofix.msrepairs.requests;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepairDetailDTO {
    private Long id;
    private Long repairId;
    private String tipoReparacion;
    private LocalDate fechaReparacion;
    private LocalTime horaReparacion;
    private BigDecimal montoReparacion;
    private String patenteVehiculo;
}