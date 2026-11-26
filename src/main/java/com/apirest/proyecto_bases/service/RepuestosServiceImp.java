package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.RepuestosModel;
import com.apirest.proyecto_bases.repository.IRepuestoRepository;

@Service
public class RepuestosServiceImp implements IRepuestosService {
    @Autowired
    IRepuestoRepository repuestoRepository;

    @Override
    public RepuestosModel crearRepuesto(RepuestosModel repuesto) {
        validarRepuesto(repuesto);
        return repuestoRepository.save(repuesto);
    }

    @Override
    public List<RepuestosModel> listarRepuestos() {
        return repuestoRepository.findAll();
    }

    @Override
    public RepuestosModel buscarRepuestoPorId(ObjectId repuestoId) {
        if (repuestoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de repuesto.");
        }
        return repuestoRepository.findById(repuestoId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El repuesto con ID " + repuestoId + " no existe."));
    }

    @Override
    public RepuestosModel actualizarRepuesto(ObjectId repuestoId, RepuestosModel repuesto) {
        buscarRepuestoPorId(repuestoId); // valida que exista
        validarRepuesto(repuesto);
        repuesto.setId(repuestoId);
        return repuestoRepository.save(repuesto);
    }

    @Override
    public void eliminarRepuesto(ObjectId repuestoId) {
        buscarRepuestoPorId(repuestoId); // valida que exista
        repuestoRepository.deleteById(repuestoId);
    }

    @Override
    public List<RepuestosModel> listarRepuestosPorNombre(String nombre) {
        return repuestoRepository.findByNombre(nombre);
    }

    // validaciones de campos obligatorios
    private void validarRepuesto(RepuestosModel repuesto) {
        if (repuesto.getNombre() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo nombre es obligatorio.");
        }
    }
}