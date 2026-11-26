package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.HistorialEstadosModel;
import com.apirest.proyecto_bases.model.OrdenServicioModel;
import com.apirest.proyecto_bases.model.ValoracionServicioModel;

public interface IOrdenServicioService {
    OrdenServicioModel crearOrdenServicio(OrdenServicioModel orden);

    List<OrdenServicioModel> listarOrdenesServicio();

    OrdenServicioModel buscarOrdenServicioPorId(ObjectId ordenId);

    OrdenServicioModel actualizarOrdenServicio(ObjectId ordenId, OrdenServicioModel orden);

    void eliminarOrdenServicio(ObjectId ordenId);

    List<OrdenServicioModel> listarOrdenesServicioPorClienteId(ObjectId clienteId);

    List<OrdenServicioModel> listarOrdenesServicioPorTecnicosAsignados(ObjectId tecnicoId);

    List<OrdenServicioModel> listarOrdenesServicioPorEstado(String estado);

    OrdenServicioModel agregarHistorialEstado(ObjectId ordenId, HistorialEstadosModel historial);
}
