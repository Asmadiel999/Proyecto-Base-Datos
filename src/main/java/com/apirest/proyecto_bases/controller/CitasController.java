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

import com.apirest.proyecto_bases.model.CitaModel;
import com.apirest.proyecto_bases.service.ICitasService;

@RestController
@RequestMapping("/UAO/apirest/citas")
public class CitasController {
    @Autowired
    ICitasService citasService;

    @PostMapping("/")
    public ResponseEntity<CitaModel> crearCita(@RequestBody CitaModel cita) {
        return new ResponseEntity<>(citasService.crearCita(cita), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CitaModel>> listarCitas() {
        return new ResponseEntity<>(citasService.listarCitas(), HttpStatus.OK);
    }

    @GetMapping("/{citaId}")
    public ResponseEntity<CitaModel> buscarCitaPorId(@PathVariable ObjectId citaId) {
        return new ResponseEntity<>(citasService.buscarCitaPorId(citaId), HttpStatus.OK);
    }

    @PutMapping("/{citaId}")
    public ResponseEntity<CitaModel> actualizarCita(@PathVariable ObjectId citaId, @RequestBody CitaModel cita) {
        return new ResponseEntity<>(citasService.actualizarCita(citaId, cita), HttpStatus.OK);
    }

    @DeleteMapping("/{citaId}")
    public ResponseEntity<Void> eliminarCita(@PathVariable ObjectId citaId) {
        citasService.eliminarCita(citaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaModel>> listarPorCliente(@PathVariable ObjectId clienteId) {
        return new ResponseEntity<>(citasService.listarCitasPorClienteId(clienteId), HttpStatus.OK);
    }

    @GetMapping("/bicicleta/{bicicletaId}")
    public ResponseEntity<List<CitaModel>> listarPorBicicleta(@PathVariable ObjectId bicicletaId) {
        return new ResponseEntity<>(citasService.listarCitasPorBicicletaId(bicicletaId), HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaModel>> listarPorEstado(@PathVariable String estado) {
        return new ResponseEntity<>(citasService.listarCitasPorEstado(estado), HttpStatus.OK);
    }
}
