package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.repository.IUsuarioRepository;

@Service
public class UsuariosServiceImp implements IUsuariosService {
    @Autowired
    IUsuarioRepository usuarioRepository;

    @Override
    public UsuarioModel crearUsuario(UsuarioModel usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioModel buscarUsuarioPorId(ObjectId usuarioId) {
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de usuario.");
        }
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El usuario con ID " + usuarioId + " no existe."));
    }

    @Override
    public UsuarioModel actualizarUsuario(ObjectId usuarioId, UsuarioModel usuario) {
        buscarUsuarioPorId(usuarioId); // valida que exista
        validarUsuario(usuario);
        usuario.setId(usuarioId);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(ObjectId usuarioId) {
        buscarUsuarioPorId(usuarioId); // valida que exista
        usuarioRepository.deleteById(usuarioId);
    }

    @Override
    public List<UsuarioModel> listarUsuariosPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    @Override
    public void validarRol(ObjectId usuarioId, String rolEsperado) {
        UsuarioModel usuario = buscarUsuarioPorId(usuarioId);
        if (usuario.getRol() == null || !usuario.getRol().equalsIgnoreCase(rolEsperado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El usuario " + usuarioId + " debe tener el rol " + rolEsperado + ".");
        }
    }

    // validaciones de campos obligatorios
    private void validarUsuario(UsuarioModel usuario) {
        if (usuario.getCorreo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo correo es obligatorio.");
        }
        if (usuario.getRol() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo rol es obligatorio.");
        }
    }
}