package com.apirest.proyecto_bases.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("trabajosOrden")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class TrabajosOrdenModel {
    @Id 
    private ObjectId id; 
    private ObjectId ordenId; 
    private ObjectId tecnicoId; 
    private ServicioResumenModel servicio; 
    private String descripcionIntervencion; 
    private String componente; 
    private Integer manoObraEstimada;
    private List<RepuestosPrevistosModel> repuestosPrevistos; 
    private AutorizacionModel autorizacion; 
    private EjecucionModel ejecucion; 
    private RevisionModel revision; 
    private GarantiaModel garantia; 
    private String observaciones; 
}
