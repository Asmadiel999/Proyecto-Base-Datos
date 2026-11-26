package com.apirest.proyecto_bases.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.OrdenServicioModel;

public interface IOrdenServicioRepository extends MongoRepository<OrdenServicioModel, ObjectId> {
    List<OrdenServicioModel> findByClienteId(ObjectId clienteId);

    List<OrdenServicioModel> findByTecnicosAsignados(ObjectId tecnicoId);

    List<OrdenServicioModel> findByEstado(String estado);

    List<OrdenServicioModel> findByBicicleta_BicicletaIdAndEstadoNotIn(ObjectId bicicletaId, List<String> estados);
}