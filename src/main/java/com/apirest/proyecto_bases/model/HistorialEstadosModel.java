package com.apirest.proyecto_bases.model;

import java.util.Date;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class HistorialEstadosModel {
    private String estado; 
    private Date fecha; 
    private ObjectId usuarioId; 
    private String observacion; 
}
