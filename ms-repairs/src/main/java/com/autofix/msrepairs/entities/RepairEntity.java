package com.autofix.msrepairs.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "repairs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehiculo_id", nullable = false)
    private Long vehicleId;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "hora_ingreso", nullable = false)
    private LocalTime entryTime;

    @Column(name = "monto_total_reparaciones", nullable = false)
    private Double totalRepairAmount;

    @Column(name = "monto_recargos", nullable = false)
    private Double surchargeAmount;

    @Column(name = "monto_descuentos", nullable = false)
    private Double discountAmount;

    @Column(name = "monto_iva", nullable = false)
    private Double taxAmount;

    @Column(name = "costo_total", nullable = false)
    private Double totalCost;

    @Column(name = "fecha_salida")
    private LocalDateTime exitDate;

    @Column(name = "hora_salida")
    private LocalTime exitTime;

    @Column(name = "fecha_retiro")
    private LocalDateTime retrievalDate;

    @Column(name = "hora_retiro")
    private LocalTime retrievalTime;
}