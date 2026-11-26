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

import com.apirest.proyecto_bases.model.OrdenServicioModel;
import com.apirest.proyecto_bases.service.IOrdenServicioService;

@RestController
@RequestMapping("/UAO/apirest/ordenservicio")

public class OrdenServicioController {
    @Autowired IOrdenServicioService ordenServicioService;
    @PostMapping("/")
    public ResponseEntity<OrdenServicioModel> crearPublicacion(@RequestBody OrdenServicioModel orden){
        return new ResponseEntity<OrdenServicioModel>(
            ordenServicioService.crearOrdenServicio(orden), HttpStatus.CREATED
        );
    }

    @GetMapping ("/")
    public ResponseEntity<List<OrdenServicioModel>> listarUsuarios(){
        return new ResponseEntity<List<OrdenServicioModel>>(
            ordenServicioService.listarOrdenesServicios(), HttpStatus.OK
        );
    }
}
