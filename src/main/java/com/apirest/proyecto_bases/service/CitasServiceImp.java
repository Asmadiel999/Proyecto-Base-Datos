package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.BicicletaModel;
import com.apirest.proyecto_bases.model.CitaModel;
import com.apirest.proyecto_bases.repository.ICitasRepository;

@Service
public class CitasServiceImp implements ICitasService {
    @Autowired
    ICitasRepository citasRepository;
    @Autowired
    IUsuariosService usuariosService;
    @Autowired
    IBicicletaService bicicletaService;
    @Autowired
    IServiciosService serviciosService;


    @Override
    public CitaModel crearCita(CitaModel cita) {
        validarCita(cita);
        return citasRepository.save(cita);
    }

    @Override
    public List<CitaModel> listarCitas() {
        return citasRepository.findAll();
    }

    @Override
    public CitaModel buscarCitaPorId(ObjectId citaId) {
        if (citaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de cita.");
        }
        return citasRepository.findById(citaId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "La cita con ID " + citaId + " no existe."));
    }

    @Override
    public CitaModel actualizarCita(ObjectId citaId, CitaModel cita) {
        buscarCitaPorId(citaId); // valida que exista
        cita.setId(citaId);
        validarCita(cita);
        return citasRepository.save(cita);
    }

    @Override
    public void eliminarCita(ObjectId citaId) {
        buscarCitaPorId(citaId); // valida que exista
        citasRepository.deleteById(citaId);
    }

    @Override
    public List<CitaModel> listarCitasPorClienteId(ObjectId clienteId) {
        return citasRepository.findByClienteId(clienteId);
    }

    @Override
    public List<CitaModel> listarCitasPorBicicletaId(ObjectId bicicletaId) {
        return citasRepository.findByBicicletaId(bicicletaId);
    }

    @Override
    public List<CitaModel> listarCitasPorEstado(String estado) {
        return citasRepository.findByEstado(estado);
    }

    // validaciones de campos obligatorios y referencias (fk) hacia otras colecciones
    private void validarCita(CitaModel cita) {
        if (cita.getClienteId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo clienteId es obligatorio.");
        }
        if (cita.getBicicletaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo bicicletaId es obligatorio.");
        }
        usuariosService.validarRol(cita.getClienteId(), "cliente");
        BicicletaModel bicicleta = bicicletaService.buscarBicicletaPorId(cita.getBicicletaId());
        // la bicicleta debe pertenecer al cliente que agenda la cita
        if (!cita.getClienteId().equals(bicicleta.getClienteId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La bicicleta " + cita.getBicicletaId() + " no pertenece al cliente " + cita.getClienteId() + ".");
        }
        if (cita.getServiciosInteres() != null) {
            for (ObjectId servicioId : cita.getServiciosInteres()) {
                serviciosService.buscarServicioPorId(servicioId);
            }
        }
        
    }
}