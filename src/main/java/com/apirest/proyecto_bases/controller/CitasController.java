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

import com.apirest.proyecto_bases.model.CitaModel;
import com.apirest.proyecto_bases.service.ICitasService;

@RestController 
@RequestMapping("/UAO/apirest/citas")

public class CitasController {
    @Autowired ICitasService citasService;
    @PostMapping("/")
    public ResponseEntity<CitaModel> crearPublicacion(@RequestBody CitaModel citas){
        return new ResponseEntity<CitaModel>(
            citasService.crearCita(citas), HttpStatus.CREATED
        );
    }

    @GetMapping ("/")
    public ResponseEntity<List<CitaModel>> listarUsuarios(){
        return new ResponseEntity<List<CitaModel>>(
            citasService.listarCitas(), HttpStatus.OK
        );
    }
}
