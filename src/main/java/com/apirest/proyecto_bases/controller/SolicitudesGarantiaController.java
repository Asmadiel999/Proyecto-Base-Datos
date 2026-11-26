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

import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;
import com.apirest.proyecto_bases.service.ISolicitudesGarantiaService;

@RestController
@RequestMapping("/UAO/apirest/garantia")
public class SolicitudesGarantiaController {
    @Autowired
    ISolicitudesGarantiaService solicitudesGarantiaService;

    @PostMapping("/")
    public ResponseEntity<SolicitudesGarantiaModel> crearSolicitudGarantia(@RequestBody SolicitudesGarantiaModel solicitudGarantia) {
        return new ResponseEntity<>(solicitudesGarantiaService.crearSolicitudGarantia(solicitudGarantia), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<SolicitudesGarantiaModel>> listarSolicitudesGarantia() {
        return new ResponseEntity<>(solicitudesGarantiaService.listarSolicitudesGarantia(), HttpStatus.OK);
    }

    @GetMapping("/{solicitudId}")
    public ResponseEntity<SolicitudesGarantiaModel> buscarSolicitudGarantiaPorId(@PathVariable ObjectId solicitudId) {
        return new ResponseEntity<>(solicitudesGarantiaService.buscarSolicitudGarantiaPorId(solicitudId), HttpStatus.OK);
    }

    @PutMapping("/{solicitudId}")
    public ResponseEntity<SolicitudesGarantiaModel> actualizarSolicitudGarantia(@PathVariable ObjectId solicitudId, @RequestBody SolicitudesGarantiaModel solicitudGarantia) {
        return new ResponseEntity<>(solicitudesGarantiaService.actualizarSolicitudGarantia(solicitudId, solicitudGarantia), HttpStatus.OK);
    }

    @DeleteMapping("/{solicitudId}")
    public ResponseEntity<Void> eliminarSolicitudGarantia(@PathVariable ObjectId solicitudId) {
        solicitudesGarantiaService.eliminarSolicitudGarantia(solicitudId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/trabajo/{trabajoId}")
    public ResponseEntity<List<SolicitudesGarantiaModel>> listarPorTrabajo(@PathVariable ObjectId trabajoId) {
        return new ResponseEntity<>(solicitudesGarantiaService.listarSolicitudesGarantiaPorTrabajoId(trabajoId), HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SolicitudesGarantiaModel>> listarPorEstado(@PathVariable String estado) {
        return new ResponseEntity<>(solicitudesGarantiaService.listarSolicitudesGarantiaPorEstado(estado), HttpStatus.OK);
    }
}
