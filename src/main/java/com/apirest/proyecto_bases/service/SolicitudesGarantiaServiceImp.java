package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;
import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.repository.ISolicitudesGarantiaRepository;

@Service 
public class SolicitudesGarantiaServiceImp implements ISolicitudesGarantiaService{
    @Autowired ISolicitudesGarantiaRepository solicitudesGarantiaRepository;
    @Autowired ITrabajosOrdenService trabajosOrdenService;

    @Override
    public SolicitudesGarantiaModel crearSolicitudGarantia(SolicitudesGarantiaModel solicitud) {
        ObjectId trabajoId = solicitud.getTrabajoId();
        TrabajosOrdenModel trabajo = trabajosOrdenService.buscarTrabajoPorId(trabajoId);

        if(trabajo != null){
            return solicitudesGarantiaRepository.save(solicitud);
        }else{
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "El trabajo no existe y por lo tanto la colección no puede ser creada");
        }
    }

    @Override
    public List<SolicitudesGarantiaModel> listarSolicitudes() {
        return solicitudesGarantiaRepository.findAll();
    }

    @Override
    public SolicitudesGarantiaModel buscarGarantiaPorId(ObjectId garantiaId) {
        Optional<SolicitudesGarantiaModel> solicitudExistente = solicitudesGarantiaRepository.findById(garantiaId);
        return solicitudExistente.orElse(null); 
    }
}
