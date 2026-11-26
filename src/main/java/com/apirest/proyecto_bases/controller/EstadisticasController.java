package com.apirest.proyecto_bases.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.apirest.proyecto_bases.service.IEstadisticaOrdenesService;
import com.apirest.proyecto_bases.service.IEstadisticaServiciosService;
import com.apirest.proyecto_bases.service.IEstadisticaTiemposService;
import com.apirest.proyecto_bases.service.IEstadisticaValoracionesService;

@RestController
@RequestMapping("/UAO/apirest/estadisticas")
public class EstadisticasController {
    @Autowired
    IEstadisticaOrdenesService ordenesPorEstadoService;
    @Autowired
    IEstadisticaServiciosService serviciosSolicitadosService;
    @Autowired
    IEstadisticaTiemposService tiemposAtencionService;
    @Autowired
    IEstadisticaValoracionesService valoracionesService;

    @GetMapping("/ordenes-por-estado")
    public ResponseEntity<List<Map<String, Object>>> ordenesPorEstado() {
        return new ResponseEntity<>(ordenesPorEstadoService.ordenesPorEstado(), HttpStatus.OK);
    }

    @GetMapping("/servicios-mas-solicitados")
    public ResponseEntity<List<Map<String, Object>>> serviciosMasSolicitados(
            @RequestParam(required = false) Integer limite) {
        return new ResponseEntity<>(serviciosSolicitadosService.serviciosMasSolicitados(limite), HttpStatus.OK);
    }

    @GetMapping("/tiempos-atencion")
    public ResponseEntity<Map<String, Object>> tiemposDeAtencion() {
        return new ResponseEntity<>(tiemposAtencionService.tiemposDeAtencion(), HttpStatus.OK);
    }

    @GetMapping("/valoraciones")
    public ResponseEntity<Map<String, Object>> resumenValoraciones() {
        return new ResponseEntity<>(valoracionesService.resumenValoraciones(), HttpStatus.OK);
    }
}