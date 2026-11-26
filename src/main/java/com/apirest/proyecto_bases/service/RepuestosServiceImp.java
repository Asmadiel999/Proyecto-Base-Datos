package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.proyecto_bases.model.RepuestosModel;
import com.apirest.proyecto_bases.repository.IRepuestoRepository;

@Service 

public class RepuestosServiceImp implements IRepuestosService{
    @Autowired IRepuestoRepository repuestoRepository;

    @Override
    public RepuestosModel crearRepuesto(RepuestosModel repuesto) {
        return repuestoRepository.save(repuesto); 
    }

    @Override
    public List<RepuestosModel> listarRepuestos() {
        return repuestoRepository.findAll(); 
    }

    @Override
    public RepuestosModel buscarRepuestosPorId(ObjectId repuestoId) {
       Optional<RepuestosModel> repuestoExistente = repuestoRepository.findById(repuestoId);
       return repuestoExistente.orElse(null);
    }
}
