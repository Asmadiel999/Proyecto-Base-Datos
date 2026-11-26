package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.UsuarioModel;

public interface IUsuariosService {
    public UsuarioModel crearUsuario(UsuarioModel usuario); 
    public List<UsuarioModel> listarUsuarios();
    public UsuarioModel buscarUsuarioPorId(ObjectId idUsuario); 
}
