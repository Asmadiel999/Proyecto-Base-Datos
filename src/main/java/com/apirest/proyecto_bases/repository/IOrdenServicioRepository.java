package com.apirest.proyecto_bases.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.OrdenServicioModel;

public interface IOrdenServicioRepository extends MongoRepository<OrdenServicioModel, ObjectId>{
    
}
