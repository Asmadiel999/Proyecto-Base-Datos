package com.apirest.proyecto_bases.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Citas")
@Data 
@AllArgsConstructor 
@NoArgsConstructor 

public class CitaModel {
    @Id
    private ObjectId id; 
    private ObjectId clienteId;
    private ObjectId bicicletaId; 
    private Date fechaHora; 
    private String motivoVisita; 
    private String estado; 
    private String problemasObservados; 
    private List<ObjectId> serviciosInteres; 
    private String observacionCambio; 
}
