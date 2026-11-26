package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.OrdenServicioModel;

public interface IOrdenServicioService {
    public OrdenServicioModel crearOrdenServicio(OrdenServicioModel orden);
    public List<OrdenServicioModel> listarOrdenesServicios();
    public OrdenServicioModel buscarOrdenServicioPorId(ObjectId ordenServicioId); 
}
