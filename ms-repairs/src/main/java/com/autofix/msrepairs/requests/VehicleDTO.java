package com.autofix.msrepairs.requests;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleDTO {
    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private String tipo;
    private int anioFabricacion;
    private String tipoMotor;
    private int numeroAsientos;
}