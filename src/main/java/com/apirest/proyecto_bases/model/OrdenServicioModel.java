package com.apirest.proyecto_bases.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("OrdenServicio")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class OrdenServicioModel {
    @Id
    private ObjectId id; 
    private ObjectId clienteId; 
    private BicicletaResumenModel bicicleta; 
    private ObjectId citaId; 
    private Date fechaRecepcion; 
    private Date fechaEntregaEstimada; 
    private ObjectId coordinadorId; 
    private List<ObjectId> tecnicosAsignados;
    private String estado; 
    private RecepcionModel recepcion; 
    private DiagnosticoModel diagnostico; 
    private EntregaModel entrega; 
    private ValoracionServicioModel valoracionServicio; 
    private List<HistorialEstadosModel> historialEstados; 
}
