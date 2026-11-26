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

import com.apirest.proyecto_bases.model.BicicletaModel;
import com.apirest.proyecto_bases.service.IBicicletaService;

@RestController
@RequestMapping("/UAO/apirest/bicicletas")
public class BicicletaController {
    @Autowired
    IBicicletaService bicicletaService;

    @PostMapping("/")
    public ResponseEntity<BicicletaModel> crearBicicleta(@RequestBody BicicletaModel bicicleta) {
        return new ResponseEntity<>(bicicletaService.crearBicicleta(bicicleta), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<BicicletaModel>> listarBicicletas() {
        return new ResponseEntity<>(bicicletaService.listarBicicletas(), HttpStatus.OK);
    }

    @GetMapping("/{bicicletaId}")
    public ResponseEntity<BicicletaModel> buscarBicicletaPorId(@PathVariable ObjectId bicicletaId) {
        return new ResponseEntity<>(bicicletaService.buscarBicicletaPorId(bicicletaId), HttpStatus.OK);
    }

    @PutMapping("/{bicicletaId}")
    public ResponseEntity<BicicletaModel> actualizarBicicleta(@PathVariable ObjectId bicicletaId, @RequestBody BicicletaModel bicicleta) {
        return new ResponseEntity<>(bicicletaService.actualizarBicicleta(bicicletaId, bicicleta), HttpStatus.OK);
    }

    @DeleteMapping("/{bicicletaId}")
    public ResponseEntity<Void> eliminarBicicleta(@PathVariable ObjectId bicicletaId) {
        bicicletaService.eliminarBicicleta(bicicletaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<BicicletaModel>> listarPorCliente(@PathVariable ObjectId clienteId) {
        return new ResponseEntity<>(bicicletaService.listarBicicletasPorClienteId(clienteId), HttpStatus.OK);
    }
}

