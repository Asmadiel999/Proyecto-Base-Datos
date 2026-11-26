package com.apirest.proyecto_bases.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 

public class ServicioResumenModel {
    private ObjectId servicioId; 
    private String nombre; 
}
