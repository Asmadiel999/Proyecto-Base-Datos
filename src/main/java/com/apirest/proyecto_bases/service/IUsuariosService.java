package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.UsuarioModel;

public interface IUsuariosService {
    UsuarioModel crearUsuario(UsuarioModel usuario);

    List<UsuarioModel> listarUsuarios();

    UsuarioModel buscarUsuarioPorId(ObjectId usuarioId);

    UsuarioModel actualizarUsuario(ObjectId usuarioId, UsuarioModel usuario);

    void eliminarUsuario(ObjectId usuarioId);

    List<UsuarioModel> listarUsuariosPorRol(String rol);

    void validarRol(ObjectId usuarioId, String rolEsperado);
}