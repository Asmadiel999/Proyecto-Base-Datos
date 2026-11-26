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

import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.service.ITrabajosOrdenService;

@RestController 
@RequestMapping("/UAO/apirest/trabajos")
public class TrabajosOrdenController {
    @Autowired ITrabajosOrdenService trabajosOrdenService;
    @PostMapping("/")
    public ResponseEntity<TrabajosOrdenModel> crearPublicacion(@RequestBody TrabajosOrdenModel trabajos){
        return new ResponseEntity<TrabajosOrdenModel>(
            trabajosOrdenService.crearTrabajo(trabajos), HttpStatus.CREATED
        );
    }

    @GetMapping ("/")
    public ResponseEntity<List<TrabajosOrdenModel>> listarUsuarios(){
        return new ResponseEntity<List<TrabajosOrdenModel>>(
            trabajosOrdenService.listarTrabajos(), HttpStatus.OK
        );
    }
}
