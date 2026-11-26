package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.BicicletaModel;

public interface IBicicletaService {
    BicicletaModel crearBicicleta(BicicletaModel bicicleta);

    List<BicicletaModel> listarBicicletas();

    BicicletaModel buscarBicicletaPorId(ObjectId bicicletaId);

    BicicletaModel actualizarBicicleta(ObjectId bicicletaId, BicicletaModel bicicleta);

    void eliminarBicicleta(ObjectId bicicletaId);

    List<BicicletaModel> listarBicicletasPorClienteId(ObjectId clienteId);
}
