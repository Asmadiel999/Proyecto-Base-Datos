package com.apirest.proyecto_bases.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class RepuestosUtilizadosModel {
    private ObjectId repuestoId; 
    private Integer cantidad; 
    private Double precioUnitarioAplicado; 
}
