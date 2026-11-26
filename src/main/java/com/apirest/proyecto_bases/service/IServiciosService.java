package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.ServicioModel;

public interface IServiciosService {
    ServicioModel crearServicio(ServicioModel servicio);

    List<ServicioModel> listarServicios();

    ServicioModel buscarServicioPorId(ObjectId servicioId);

    ServicioModel actualizarServicio(ObjectId servicioId, ServicioModel servicio);

    void eliminarServicio(ObjectId servicioId);

    List<ServicioModel> listarServiciosPorCategoria(String categoria);
}