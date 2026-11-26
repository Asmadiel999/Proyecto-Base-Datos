package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.OrdenServicioModel;
import com.apirest.proyecto_bases.model.ValoracionServicioModel;

public interface IOrdenServicioService {
    public OrdenServicioModel crearOrdenServicio(OrdenServicioModel orden);
    public List<OrdenServicioModel> listarOrdenesServicios();
    public OrdenServicioModel buscarOrdenServicioPorId(ObjectId ordenServicioId);
    public OrdenServicioModel actualizarEstado (ObjectId ordenId, String nuevoEstado, ObjectId usuarioId, String observacion);
    public OrdenServicioModel registrarValoracion(ObjectId ordenId, ObjectId clienteId, ValoracionServicioModel valoracion);

}
