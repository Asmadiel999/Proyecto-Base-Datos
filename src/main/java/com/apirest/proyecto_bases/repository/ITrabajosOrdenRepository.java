package com.apirest.proyecto_bases.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.TrabajosOrdenModel;

public interface ITrabajosOrdenRepository extends MongoRepository<TrabajosOrdenModel, ObjectId>{
    List<TrabajosOrdenModel> findByOrdenId(ObjectId id); 
    List<TrabajosOrdenModel> findByTecnicoId(ObjectId tecnicoId);
}
