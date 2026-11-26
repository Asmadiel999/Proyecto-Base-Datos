package com.apirest.proyecto_bases.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class EjecucionModel {
    private Date fechaInicio; 
    private Date fechaFinalizacion; 
    private String estado; 
    private String descripcionRealizada; 
    private List<RepuestosUtilizadosModel> repuestosUtilizados; 
}
