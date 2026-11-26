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

import com.apirest.proyecto_bases.model.HistorialEstadosModel;
import com.apirest.proyecto_bases.model.OrdenServicioModel;
import com.apirest.proyecto_bases.service.IOrdenServicioService;

@RestController
@RequestMapping("/UAO/apirest/ordenservicio")
public class OrdenServicioController {
    @Autowired
    IOrdenServicioService ordenServicioService;

    @PostMapping("/")
    public ResponseEntity<OrdenServicioModel> crearOrdenServicio(@RequestBody OrdenServicioModel ordenServicio) {
        return new ResponseEntity<>(ordenServicioService.crearOrdenServicio(ordenServicio), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenServicioModel>> listarOrdenesServicio() {
        return new ResponseEntity<>(ordenServicioService.listarOrdenesServicio(), HttpStatus.OK);
    }

    @GetMapping("/{ordenId}")
    public ResponseEntity<OrdenServicioModel> buscarOrdenServicioPorId(@PathVariable ObjectId ordenId) {
        return new ResponseEntity<>(ordenServicioService.buscarOrdenServicioPorId(ordenId), HttpStatus.OK);
    }

    @PutMapping("/{ordenId}")
    public ResponseEntity<OrdenServicioModel> actualizarOrdenServicio(@PathVariable ObjectId ordenId, @RequestBody OrdenServicioModel ordenServicio) {
        return new ResponseEntity<>(ordenServicioService.actualizarOrdenServicio(ordenId, ordenServicio), HttpStatus.OK);
    }

    @DeleteMapping("/{ordenId}")
    public ResponseEntity<Void> eliminarOrdenServicio(@PathVariable ObjectId ordenId) {
        ordenServicioService.eliminarOrdenServicio(ordenId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<OrdenServicioModel>> listarPorCliente(@PathVariable ObjectId clienteId) {
        return new ResponseEntity<>(ordenServicioService.listarOrdenesServicioPorClienteId(clienteId), HttpStatus.OK);
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<OrdenServicioModel>> listarPorTecnico(@PathVariable ObjectId tecnicoId) {
        return new ResponseEntity<>(ordenServicioService.listarOrdenesServicioPorTecnicosAsignados(tecnicoId), HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenServicioModel>> listarPorEstado(@PathVariable String estado) {
        return new ResponseEntity<>(ordenServicioService.listarOrdenesServicioPorEstado(estado), HttpStatus.OK);
    }

    @PostMapping("/{ordenId}/historial")
    public ResponseEntity<OrdenServicioModel> agregarHistorialEstado(@PathVariable ObjectId ordenId, @RequestBody HistorialEstadosModel historial) {
        return new ResponseEntity<>(ordenServicioService.agregarHistorialEstado(ordenId, historial), HttpStatus.CREATED);
    }
}
