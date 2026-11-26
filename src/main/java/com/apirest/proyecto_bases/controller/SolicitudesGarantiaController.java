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

import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;
import com.apirest.proyecto_bases.service.ISolicitudesGarantiaService;

@RestController
@RequestMapping("/UAO/apirest/garantia")
public class SolicitudesGarantiaController {
    @Autowired ISolicitudesGarantiaService solicitudesGarantiaService;
    @PostMapping("/")
    public ResponseEntity<SolicitudesGarantiaModel> crearPublicacion(@RequestBody SolicitudesGarantiaModel solicitud){
        return new ResponseEntity<SolicitudesGarantiaModel>(
            solicitudesGarantiaService.crearSolicitudGarantia(solicitud), HttpStatus.CREATED
        );
    }

    @GetMapping ("/")
    public ResponseEntity<List<SolicitudesGarantiaModel>> listarUsuarios(){
        return new ResponseEntity<List<SolicitudesGarantiaModel>>(
            solicitudesGarantiaService.listarSolicitudes(), HttpStatus.OK
        );
    }
}
