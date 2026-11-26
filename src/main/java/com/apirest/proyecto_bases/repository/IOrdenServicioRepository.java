package com.apirest.proyecto_bases.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.OrdenServicioModel;


import java.util.List;

public interface IOrdenServicioRepository extends MongoRepository<OrdenServicioModel, ObjectId> {
    boolean existsByBicicleta_BicicletaIdAndEstadoIn(ObjectId bicicletaId, List<String> estados);
}