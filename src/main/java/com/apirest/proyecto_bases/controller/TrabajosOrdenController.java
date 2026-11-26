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

import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.service.ITrabajosOrdenService;

@RestController
@RequestMapping("/UAO/apirest/trabajos")
public class TrabajosOrdenController {
    @Autowired
    ITrabajosOrdenService trabajosOrdenService;

    @PostMapping("/")
    public ResponseEntity<TrabajosOrdenModel> crearTrabajoOrden(@RequestBody TrabajosOrdenModel trabajoOrden) {
        return new ResponseEntity<>(trabajosOrdenService.crearTrabajoOrden(trabajoOrden), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<TrabajosOrdenModel>> listarTrabajosOrden() {
        return new ResponseEntity<>(trabajosOrdenService.listarTrabajosOrden(), HttpStatus.OK);
    }

    @GetMapping("/{trabajoId}")
    public ResponseEntity<TrabajosOrdenModel> buscarTrabajoOrdenPorId(@PathVariable ObjectId trabajoId) {
        return new ResponseEntity<>(trabajosOrdenService.buscarTrabajoOrdenPorId(trabajoId), HttpStatus.OK);
    }

    @PutMapping("/{trabajoId}")
    public ResponseEntity<TrabajosOrdenModel> actualizarTrabajoOrden(@PathVariable ObjectId trabajoId, @RequestBody TrabajosOrdenModel trabajoOrden) {
        return new ResponseEntity<>(trabajosOrdenService.actualizarTrabajoOrden(trabajoId, trabajoOrden), HttpStatus.OK);
    }

    @DeleteMapping("/{trabajoId}")
    public ResponseEntity<Void> eliminarTrabajoOrden(@PathVariable ObjectId trabajoId) {
        trabajosOrdenService.eliminarTrabajoOrden(trabajoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<TrabajosOrdenModel>> listarPorOrden(@PathVariable ObjectId ordenId) {
        return new ResponseEntity<>(trabajosOrdenService.listarTrabajosOrdenPorOrdenId(ordenId), HttpStatus.OK);
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<TrabajosOrdenModel>> listarPorTecnico(@PathVariable ObjectId tecnicoId) {
        return new ResponseEntity<>(trabajosOrdenService.listarTrabajosOrdenPorTecnicoId(tecnicoId), HttpStatus.OK);
    }

    @GetMapping("/orden/{ordenId}/valor-estimado")
    public ResponseEntity<Long> calcularValorEstimado(@PathVariable ObjectId ordenId) {
        return new ResponseEntity<>(trabajosOrdenService.calcularValorEstimado(ordenId), HttpStatus.OK);
    }
}
