package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.TrabajosOrdenModel;

public interface ITrabajosOrdenService {
    TrabajosOrdenModel crearTrabajoOrden(TrabajosOrdenModel trabajo);

    List<TrabajosOrdenModel> listarTrabajosOrden();

    TrabajosOrdenModel buscarTrabajoOrdenPorId(ObjectId trabajoId);

    TrabajosOrdenModel actualizarTrabajoOrden(ObjectId trabajoId, TrabajosOrdenModel trabajo);

    void eliminarTrabajoOrden(ObjectId trabajoId);

    List<TrabajosOrdenModel> listarTrabajosOrdenPorOrdenId(ObjectId ordenId);

    List<TrabajosOrdenModel> listarTrabajosOrdenPorTecnicoId(ObjectId tecnicoId);

    Long calcularValorEstimado(ObjectId ordenId);
}