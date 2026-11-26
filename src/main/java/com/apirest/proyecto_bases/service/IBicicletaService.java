package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.BicicletaModel;

public interface IBicicletaService {
    public BicicletaModel crearBicicleta(BicicletaModel bicicleta); 
    public List<BicicletaModel> listarBicicletas();
    public BicicletaModel buscarBicicletaPorId(ObjectId idBicicleta);
}
