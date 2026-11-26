package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;

public interface ISolicitudesGarantiaService {
    SolicitudesGarantiaModel crearSolicitudGarantia(SolicitudesGarantiaModel solicitud);

    List<SolicitudesGarantiaModel> listarSolicitudesGarantia();

    SolicitudesGarantiaModel buscarSolicitudGarantiaPorId(ObjectId solicitudId);

    SolicitudesGarantiaModel actualizarSolicitudGarantia(ObjectId solicitudId, SolicitudesGarantiaModel solicitud);

    void eliminarSolicitudGarantia(ObjectId solicitudId);

    List<SolicitudesGarantiaModel> listarSolicitudesGarantiaPorTrabajoId(ObjectId trabajoId);

    List<SolicitudesGarantiaModel> listarSolicitudesGarantiaPorEstado(String estado);
}