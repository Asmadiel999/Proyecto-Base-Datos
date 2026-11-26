package com.apirest.proyecto_bases.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.UsuarioModel;

public interface IUsuarioRepository extends MongoRepository<UsuarioModel, ObjectId>{
    List<UsuarioModel> findByRol(String rol);
}
