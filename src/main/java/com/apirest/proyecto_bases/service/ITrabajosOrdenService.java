package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.TrabajosOrdenModel;

public interface ITrabajosOrdenService {
    public TrabajosOrdenModel crearTrabajo(TrabajosOrdenModel trabajos);
    public List<TrabajosOrdenModel> listarTrabajos();
    public TrabajosOrdenModel buscarTrabajoPorId(ObjectId trabajoId); 
}
