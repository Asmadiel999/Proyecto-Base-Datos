package com.apirest.proyecto_bases.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class EvidenciaDosModel {
    private String nombreArchivo; 
    private String descripcion;
    private String ubicacion;
}
