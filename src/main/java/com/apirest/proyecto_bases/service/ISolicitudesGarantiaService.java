package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;

public interface ISolicitudesGarantiaService {
    public SolicitudesGarantiaModel crearSolicitudGarantia(SolicitudesGarantiaModel solicitud);
    public List<SolicitudesGarantiaModel> listarSolicitudes();
    public SolicitudesGarantiaModel buscarGarantiaPorId(ObjectId garantiaId); 
}
