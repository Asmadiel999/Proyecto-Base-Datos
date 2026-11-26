package com.apirest.proyecto_bases.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 

public class RevisionModel {
    private String resultado; 
    private boolean aprobada; 
    private String recomendaciones; 
}
