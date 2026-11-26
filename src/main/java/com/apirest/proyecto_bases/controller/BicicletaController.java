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

import com.apirest.proyecto_bases.model.BicicletaModel;
import com.apirest.proyecto_bases.service.IBicicletaService;

@RestController 
@RequestMapping("/UAO/apirest/bicicletas")
public class BicicletaController {
    @Autowired IBicicletaService bicicletaService;
    @PostMapping("/")
    public ResponseEntity<BicicletaModel> crearPublicacion(@RequestBody BicicletaModel bicicleta){
        return new ResponseEntity<BicicletaModel>(
            bicicletaService.crearBicicleta(bicicleta), HttpStatus.CREATED
        );
    }

    @GetMapping ("/")
    public ResponseEntity<List<BicicletaModel>> listarUsuarios(){
        return new ResponseEntity<List<BicicletaModel>>(
            bicicletaService.listarBicicletas(), HttpStatus.OK
        );
    }
    
}
