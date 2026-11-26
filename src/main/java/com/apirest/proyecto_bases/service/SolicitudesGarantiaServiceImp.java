package com.apirest.proyecto_bases.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;
import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.repository.ISolicitudesGarantiaRepository;

@Service 
public class SolicitudesGarantiaServiceImp implements ISolicitudesGarantiaService {
    @Autowired ISolicitudesGarantiaRepository solicitudesGarantiaRepository;
    @Autowired ITrabajosOrdenService trabajosOrdenService;

    @Override
    public SolicitudesGarantiaModel crearSolicitudGarantia(SolicitudesGarantiaModel solicitud) {
        ObjectId trabajoId = solicitud.getTrabajoId();
        TrabajosOrdenModel trabajo = (trabajoId != null) ? trabajosOrdenService.buscarTrabajoPorId(trabajoId) : null;
        if (trabajo != null) {

            if (trabajo.getGarantia() == null || 
                trabajo.getGarantia().getFechaInicio() == null || 
                trabajo.getGarantia().getDuracion() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "El trabajo original no cuenta con una garantía registrada");
            }

            Date fechaSolicitud = (solicitud.getFecha() != null) ? solicitud.getFecha() : new Date();
            solicitud.setFecha(fechaSolicitud);

            Calendar cal = Calendar.getInstance();
            cal.setTime(trabajo.getGarantia().getFechaInicio());
            cal.add(Calendar.DAY_OF_YEAR, trabajo.getGarantia().getDuracion());
            Date fechaVencimiento = cal.getTime();

            if (fechaSolicitud.before(trabajo.getGarantia().getFechaInicio()) || fechaSolicitud.after(fechaVencimiento)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "La garantía del trabajo ya se encuentra vencida o fuera del periodo vigente");
            }

            return solicitudesGarantiaRepository.save(solicitud);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "El trabajo no existe y por lo tanto la colección no puede ser creada");
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