package com.apirest.proyecto_bases.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;

public interface ISolicitudesGarantiaRepository extends MongoRepository<SolicitudesGarantiaModel, ObjectId>{
    
}
