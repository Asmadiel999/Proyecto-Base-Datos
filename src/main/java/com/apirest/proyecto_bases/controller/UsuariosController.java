package com.apirest.proyecto_bases.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.service.IUsuariosService;

@RestController
@RequestMapping("/UAO/apirest/usuarios")
public class UsuariosController {
    @Autowired
    IUsuariosService usuariosService;

    @PostMapping("/")
    public ResponseEntity<UsuarioModel> crearUsuario(@RequestBody UsuarioModel usuario) {
        return new ResponseEntity<>(usuariosService.crearUsuario(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        return new ResponseEntity<>(usuariosService.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@PathVariable ObjectId usuarioId) {
        return new ResponseEntity<>(usuariosService.buscarUsuarioPorId(usuarioId), HttpStatus.OK);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioModel> actualizarUsuario(@PathVariable ObjectId usuarioId, @RequestBody UsuarioModel usuario) {
        return new ResponseEntity<>(usuariosService.actualizarUsuario(usuarioId, usuario), HttpStatus.OK);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable ObjectId usuarioId) {
        usuariosService.eliminarUsuario(usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuarioModel>> listarPorRol(@PathVariable String rol) {
        return new ResponseEntity<>(usuariosService.listarUsuariosPorRol(rol), HttpStatus.OK);
    }
}