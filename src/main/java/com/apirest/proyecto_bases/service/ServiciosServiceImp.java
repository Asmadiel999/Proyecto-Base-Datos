package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.ServicioModel;
import com.apirest.proyecto_bases.repository.IServiciosRepository;

@Service
public class ServiciosServiceImp implements IServiciosService {
    @Autowired
    IServiciosRepository serviciosRepository;

    @Override
    public ServicioModel crearServicio(ServicioModel servicio) {
        validarServicio(servicio);
        return serviciosRepository.save(servicio);
    }

    @Override
    public List<ServicioModel> listarServicios() {
        return serviciosRepository.findAll();
    }

    @Override
    public ServicioModel buscarServicioPorId(ObjectId servicioId) {
        if (servicioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de servicio.");
        }
        return serviciosRepository.findById(servicioId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El servicio con ID " + servicioId + " no existe."));
    }

    @Override
    public ServicioModel actualizarServicio(ObjectId servicioId, ServicioModel servicio) {
        buscarServicioPorId(servicioId); // valida que exista
        validarServicio(servicio);
        servicio.setId(servicioId);
        return serviciosRepository.save(servicio);
    }

    @Override
    public void eliminarServicio(ObjectId servicioId) {
        buscarServicioPorId(servicioId); // valida que exista
        serviciosRepository.deleteById(servicioId);
    }

    @Override
    public List<ServicioModel> listarServiciosPorCategoria(String categoria) {
        return serviciosRepository.findByCategoria(categoria);
    }

    // validaciones de campos obligatorios
    private void validarServicio(ServicioModel servicio) {
        if (servicio.getNombre() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo nombre es obligatorio.");
        }
    }
}