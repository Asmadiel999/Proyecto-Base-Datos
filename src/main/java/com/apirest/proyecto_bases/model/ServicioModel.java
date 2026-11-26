package com.apirest.proyecto_bases.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Servicios")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class ServicioModel {
    @Id 
    private ObjectId id; 
    private String nombre; 
    private String categoria; 
    private String descripcion; 
    private long duracionEstimada;
    private double precioReferencia; 
    private List<TareasHabitualesModel> tareasHabituales;
    private String recomendacionesGenerales; 
}
