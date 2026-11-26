package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.BicicletaModel;
import com.apirest.proyecto_bases.model.CitaModel;
import com.apirest.proyecto_bases.model.OrdenServicioModel;
import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.repository.IOrdenServicioRepository;

@Service 
public class OrdenServicioServiceImp implements IOrdenServicioService {
    @Autowired IOrdenServicioRepository ordenServicioRepository;
    @Autowired IUsuariosService usuariosService;
    @Autowired IBicicletaService bicicletaService;
    @Autowired ICitasService citasService;

    @Override
    public OrdenServicioModel crearOrdenServicio(OrdenServicioModel orden) {
        ObjectId usuarioId = orden.getClienteId();
        ObjectId bicicletaId = orden.getBicicleta().getBicicletaId(); 
        ObjectId citaId = orden.getCitaId(); 

        UsuarioModel usuario = usuariosService.buscarUsuarioPorId(usuarioId);
        BicicletaModel bicicleta = bicicletaService.buscarBicicletaPorId(bicicletaId);
        CitaModel cita = citasService.buscarCitaPorId(citaId);

        if (usuario != null && bicicleta != null && cita != null) {
            return ordenServicioRepository.save(orden);            
        }else{
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "El usuario, la bicicleta y la cita no existen, por ende no se puede generar la colección");
        }
    }

    @Override
    public List<OrdenServicioModel> listarOrdenesServicios() {
        return ordenServicioRepository.findAll();
    }

    @Override
    public OrdenServicioModel buscarOrdenServicioPorId(ObjectId ordenServicioId) {
        Optional<OrdenServicioModel> ordenServicioExistente = ordenServicioRepository.findById(ordenServicioId);
        return ordenServicioExistente.orElse(null); 
    }
}
