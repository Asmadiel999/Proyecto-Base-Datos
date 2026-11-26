package com.apirest.proyecto_bases.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class ValoracionServicioModel {
    private int calificacion; 
    private String comentarios; 
    private String sugerencias; 
}
