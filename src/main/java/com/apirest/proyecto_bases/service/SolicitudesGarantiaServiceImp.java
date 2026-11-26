package com.apirest.proyecto_bases.service;

import java.util.Calendar;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.GarantiaModel;
import com.apirest.proyecto_bases.model.SolicitudesGarantiaModel;
import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.repository.ISolicitudesGarantiaRepository;

@Service
public class SolicitudesGarantiaServiceImp implements ISolicitudesGarantiaService {
    @Autowired
    ISolicitudesGarantiaRepository solicitudesGarantiaRepository;
    @Autowired
    ITrabajosOrdenService trabajosOrdenService;

    @Override
    public SolicitudesGarantiaModel crearSolicitudGarantia(SolicitudesGarantiaModel solicitud) {
        validarSolicitudGarantia(solicitud);
        return solicitudesGarantiaRepository.save(solicitud);
    }

    @Override
    public List<SolicitudesGarantiaModel> listarSolicitudesGarantia() {
        return solicitudesGarantiaRepository.findAll();
    }

    @Override
    public SolicitudesGarantiaModel buscarSolicitudGarantiaPorId(ObjectId solicitudId) {
        if (solicitudId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de solicitud de garantía.");
        }
        return solicitudesGarantiaRepository.findById(solicitudId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "La solicitud de garantía con ID " + solicitudId + " no existe."));
    }

    @Override
    public SolicitudesGarantiaModel actualizarSolicitudGarantia(ObjectId solicitudId,
            SolicitudesGarantiaModel solicitud) {
        buscarSolicitudGarantiaPorId(solicitudId); // valida que exista
        validarSolicitudGarantia(solicitud);
        solicitud.setId(solicitudId);
        return solicitudesGarantiaRepository.save(solicitud);
    }

    @Override
    public void eliminarSolicitudGarantia(ObjectId solicitudId) {
        buscarSolicitudGarantiaPorId(solicitudId); // valida que exista
        solicitudesGarantiaRepository.deleteById(solicitudId);
    }

    @Override
    public List<SolicitudesGarantiaModel> listarSolicitudesGarantiaPorTrabajoId(ObjectId trabajoId) {
        return solicitudesGarantiaRepository.findByTrabajoId(trabajoId);
    }

    @Override
    public List<SolicitudesGarantiaModel> listarSolicitudesGarantiaPorEstado(String estado) {
        return solicitudesGarantiaRepository.findByEstado(estado);
    }

    // validaciones de campos obligatorios y referencias (fk) hacia otras colecciones
    private void validarSolicitudGarantia(SolicitudesGarantiaModel solicitud) {
        if (solicitud.getTrabajoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo trabajoId es obligatorio.");
        }
        TrabajosOrdenModel trabajo = trabajosOrdenService.buscarTrabajoOrdenPorId(solicitud.getTrabajoId());
        GarantiaModel garantia = trabajo.getGarantia();
        if (garantia == null || garantia.getFechaInicio() == null || garantia.getDuracion() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El trabajo no tiene una garantía asociada.");
        }
        if (solicitud.getFecha() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo fecha es obligatorio.");
        }
        // la solicitud debe estar dentro de la vigencia de la garantía (duración en meses)
        Calendar vencimiento = Calendar.getInstance();
        vencimiento.setTime(garantia.getFechaInicio());
        vencimiento.add(Calendar.MONTH, garantia.getDuracion());
        if (solicitud.getFecha().before(garantia.getFechaInicio())
                || solicitud.getFecha().after(vencimiento.getTime())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La fecha de la solicitud está fuera de la vigencia de la garantía.");
        }
    }
}