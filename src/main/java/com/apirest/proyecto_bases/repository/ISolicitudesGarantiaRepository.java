package com.apirest.proyecto_bases.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;

public interface ISolicitudesGarantiaRepository extends MongoRepository<SolicitudesGarantiaModel, ObjectId>{
    List<SolicitudesGarantiaModel> findByTrabajoId(ObjectId idTrabajo); 
    List<SolicitudesGarantiaModel> findByEstado(String estado); 
}
