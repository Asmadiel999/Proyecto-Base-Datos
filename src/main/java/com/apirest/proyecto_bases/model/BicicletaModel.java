package com.apirest.proyecto_bases.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Bicicletas")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class BicicletaModel {
    @Id 
    private ObjectId id;
    private ObjectId clienteId;
    private String marca;
    private String modelo; 
    private String tipo; 
    private String color;
    private String numeroSerie; 
    private CaracteristicasTecnicasModel caracteristicasTecnicas; 
}
