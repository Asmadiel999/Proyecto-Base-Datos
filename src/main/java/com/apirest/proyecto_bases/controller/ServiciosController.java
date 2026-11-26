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

import com.apirest.proyecto_bases.model.ServicioModel;
import com.apirest.proyecto_bases.service.IServiciosService;

@RestController 
@RequestMapping("/UAO/apirest/servicios")
public class ServiciosController {
   @Autowired IServiciosService serviciosService;
    @PostMapping("/")
    public ResponseEntity<ServicioModel> crearPublicacion(@RequestBody ServicioModel servicio){
        return new ResponseEntity<ServicioModel>(
            serviciosService.crearServicio(servicio), HttpStatus.CREATED
        );
    }

    @GetMapping ("/")
    public ResponseEntity<List<ServicioModel>> listarUsuarios(){
        return new ResponseEntity<List<ServicioModel>>(
            serviciosService.listarUsuario(), HttpStatus.OK
        );
    }
}
