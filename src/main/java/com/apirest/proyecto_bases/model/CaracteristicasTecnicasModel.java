package com.apirest.proyecto_bases.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class CaracteristicasTecnicasModel {
    private String tamañoMarco; 
    private String material; 
    private String tamañoRueda;
    private String tipoFrenos; 
    private String transmision; 
    private SuspensionModel suspension; 
    private BateriaModel bateria; 
    private MotorModel motor; 
}
