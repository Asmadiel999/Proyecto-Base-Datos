package com.apirest.proyecto_bases.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("repuestos")
@NoArgsConstructor 
@AllArgsConstructor 
@Data 


public class RepuestosModel {
    @Id 
    private ObjectId id; 
    private String nombre; 
    private String especificaciones; 
    private double precio;
}
