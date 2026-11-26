package com.apirest.proyecto_bases.service;


import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.repository.IUsuarioRepository;

@Service 
public class UsuariosServiceImp implements IUsuariosService{
    @Autowired IUsuarioRepository usuarioRepository; 
    @Override
    public UsuarioModel crearUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll(); 
    }

    @Override
    public UsuarioModel buscarUsuarioPorId(ObjectId idUsuario) {
        Optional<UsuarioModel> usuarioExistente = usuarioRepository.findById(idUsuario);
        return usuarioExistente.orElse(null);
    }
}
