package com.apirest.proyecto_bases.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("SolicitudesGarantia")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class SolicitudesGarantiaModel {
    @Id 
    private ObjectId id;
    private ObjectId trabajoId;
    private String descripcion; 
    private Date fecha; 
    private List<EvidenciaDosModel> evidencias; 
    private String estado; 
    private boolean procede; 
    private String solucionAplicada; 
}
