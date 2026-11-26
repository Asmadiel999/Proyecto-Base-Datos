package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.proyecto_bases.model.ServicioModel;
import com.apirest.proyecto_bases.repository.IServiciosRepository;

@Service 

public class ServiciosServiceImp implements IServiciosService{
    @Autowired IServiciosRepository servicioRepository; 

    @Override
    public ServicioModel crearServicio(ServicioModel servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public List<ServicioModel> listarUsuario() {
        return servicioRepository.findAll(); 
    }

    @Override
    public ServicioModel buscarServicioPorId(ObjectId servicioId) {
        Optional<ServicioModel> servicioExistente = servicioRepository.findById(servicioId);
        return servicioExistente.orElse(null);
    }
}
