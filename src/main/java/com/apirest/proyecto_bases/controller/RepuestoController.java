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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.proyecto_bases.model.RepuestosModel;
import com.apirest.proyecto_bases.service.IRepuestosService;

@RestController
@RequestMapping("/UAO/apirest/repuestos")
public class RepuestoController {
    @Autowired
    IRepuestosService repuestosService;

    @PostMapping("/")
    public ResponseEntity<RepuestosModel> crearRepuesto(@RequestBody RepuestosModel repuesto) {
        return new ResponseEntity<>(repuestosService.crearRepuesto(repuesto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<RepuestosModel>> listarRepuestos() {
        return new ResponseEntity<>(repuestosService.listarRepuestos(), HttpStatus.OK);
    }

    @GetMapping("/{repuestoId}")
    public ResponseEntity<RepuestosModel> buscarRepuestoPorId(@PathVariable ObjectId repuestoId) {
        return new ResponseEntity<>(repuestosService.buscarRepuestoPorId(repuestoId), HttpStatus.OK);
    }

    @PutMapping("/{repuestoId}")
    public ResponseEntity<RepuestosModel> actualizarRepuesto(@PathVariable ObjectId repuestoId, @RequestBody RepuestosModel repuesto) {
        return new ResponseEntity<>(repuestosService.actualizarRepuesto(repuestoId, repuesto), HttpStatus.OK);
    }

    @DeleteMapping("/{repuestoId}")
    public ResponseEntity<Void> eliminarRepuesto(@PathVariable ObjectId repuestoId) {
        repuestosService.eliminarRepuesto(repuestoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<RepuestosModel>> buscarPorNombre(@RequestParam String nombre) {
        return new ResponseEntity<>(repuestosService.listarRepuestosPorNombre(nombre), HttpStatus.OK);
    }
}
