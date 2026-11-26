package com.apirest.proyecto_bases.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class RepuestosPrevistosModel {
    private ObjectId repuestoId; 
    private String nombre; 
    private String especificaciones; 
    private Integer cantidad; 
    private Double precioUnitarioEstimado; 
}
