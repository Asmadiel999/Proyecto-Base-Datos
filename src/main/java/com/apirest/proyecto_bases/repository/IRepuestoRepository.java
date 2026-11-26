package com.apirest.proyecto_bases.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.RepuestosModel;

public interface IRepuestoRepository extends MongoRepository<RepuestosModel, ObjectId>{
    List<RepuestosModel> findByNombre(String nombre);
}
