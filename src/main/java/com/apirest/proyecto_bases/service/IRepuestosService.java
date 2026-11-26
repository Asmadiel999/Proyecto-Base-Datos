package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.RepuestosModel;

public interface IRepuestosService {
    RepuestosModel crearRepuesto(RepuestosModel repuesto);

    List<RepuestosModel> listarRepuestos();

    RepuestosModel buscarRepuestoPorId(ObjectId repuestoId);

    RepuestosModel actualizarRepuesto(ObjectId repuestoId, RepuestosModel repuesto);

    void eliminarRepuesto(ObjectId repuestoId);

    List<RepuestosModel> listarRepuestosPorNombre(String nombre);
}