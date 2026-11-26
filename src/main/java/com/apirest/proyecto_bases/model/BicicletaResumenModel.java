package com.apirest.proyecto_bases.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class BicicletaResumenModel {
    private ObjectId bicicletaId; 
    private String marca; 
    private String modelo; 
}
