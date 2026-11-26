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

import com.apirest.proyecto_bases.model.RepuestosModel;
import com.apirest.proyecto_bases.service.IRepuestosService;

@RestController 
@RequestMapping("/UAO/apirest/repuestos")
public class RepuestoController {
    @Autowired IRepuestosService repuestosService;
    @PostMapping("/")
    public ResponseEntity<RepuestosModel> crearPublicacion(@RequestBody RepuestosModel repuestos){
        return new ResponseEntity<RepuestosModel>(
            repuestosService.crearRepuesto(repuestos), HttpStatus.CREATED
        );
    }

    @GetMapping ("/")
    public ResponseEntity<List<RepuestosModel>> listarUsuarios(){
        return new ResponseEntity<List<RepuestosModel>>(
            repuestosService.listarRepuestos(), HttpStatus.OK
        );
    }
}
