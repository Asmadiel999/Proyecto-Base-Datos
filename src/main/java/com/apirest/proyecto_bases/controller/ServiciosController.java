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

import com.apirest.proyecto_bases.model.ServicioModel;
import com.apirest.proyecto_bases.service.IServiciosService;

@RestController
@RequestMapping("/UAO/apirest/servicios")
public class ServiciosController {
    @Autowired
    IServiciosService serviciosService;

    @PostMapping("/")
    public ResponseEntity<ServicioModel> crearServicio(@RequestBody ServicioModel servicio) {
        return new ResponseEntity<>(serviciosService.crearServicio(servicio), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ServicioModel>> listarServicios() {
        return new ResponseEntity<>(serviciosService.listarServicios(), HttpStatus.OK);
    }

    @GetMapping("/{servicioId}")
    public ResponseEntity<ServicioModel> buscarServicioPorId(@PathVariable ObjectId servicioId) {
        return new ResponseEntity<>(serviciosService.buscarServicioPorId(servicioId), HttpStatus.OK);
    }

    @PutMapping("/{servicioId}")
    public ResponseEntity<ServicioModel> actualizarServicio(@PathVariable ObjectId servicioId, @RequestBody ServicioModel servicio) {
        return new ResponseEntity<>(serviciosService.actualizarServicio(servicioId, servicio), HttpStatus.OK);
    }

    @DeleteMapping("/{servicioId}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable ObjectId servicioId) {
        serviciosService.eliminarServicio(servicioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ServicioModel>> listarPorCategoria(@PathVariable String categoria) {
        return new ResponseEntity<>(serviciosService.listarServiciosPorCategoria(categoria), HttpStatus.OK);
    }
}

