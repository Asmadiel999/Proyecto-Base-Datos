package com.apirest.proyecto_bases.model;

import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class HallazgosModel {
    private ObjectId tecnicoId; 
    private String componente; 
    private String condicion; 
    private String descripcion; 
    private String prioridad; 
    private String observaciones; 
    private String accionesRecomendadas; 
    private List<EvidenciasModel> evidencias; 
}
