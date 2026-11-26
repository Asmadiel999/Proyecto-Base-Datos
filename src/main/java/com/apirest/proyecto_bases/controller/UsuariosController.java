package com.apirest.proyecto_bases.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.service.IUsuariosService;

@RestController 
@RequestMapping("/UAO/apirest/usuarios")
public class UsuariosController {
    @Autowired IUsuariosService usuariosService;
    @PostMapping("/")
    public ResponseEntity<UsuarioModel> crearPublicacion(@RequestBody UsuarioModel usuario){
        return new ResponseEntity<UsuarioModel>(
            usuariosService.crearUsuario(usuario), HttpStatus.CREATED
        );
    }

    @GetMapping ("/")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios(){
        return new ResponseEntity<List<UsuarioModel>>(
            usuariosService.listarUsuarios(), HttpStatus.OK
        );
    }
}
