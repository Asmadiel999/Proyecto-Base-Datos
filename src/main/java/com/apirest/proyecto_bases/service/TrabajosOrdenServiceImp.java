package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.OrdenServicioModel;
import com.apirest.proyecto_bases.model.ServicioModel;
import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.repository.ITrabajosOrdenRepository;

@Service 

public class TrabajosOrdenServiceImp implements ITrabajosOrdenService{
    @Autowired ITrabajosOrdenRepository trabajosOrdenRepository;
    @Autowired IUsuariosService usuariosService;
    @Autowired IOrdenServicioService ordenServicioService;
    @Autowired IServiciosService serviciosService;

    @Override
    public TrabajosOrdenModel crearTrabajo(TrabajosOrdenModel trabajos) {
        ObjectId usuarioId = trabajos.getTecnicoId();
        ObjectId ordenServicioId = trabajos.getOrdenId();
        ObjectId servicioId = trabajos.getServicio().getServicioId();

        UsuarioModel usuario = usuariosService.buscarUsuarioPorId(usuarioId);
        OrdenServicioModel orden = ordenServicioService.buscarOrdenServicioPorId(ordenServicioId);
        ServicioModel servicio = serviciosService.buscarServicioPorId(servicioId);

        if (usuario != null && orden != null && servicio != null) {
            return trabajosOrdenRepository.save(trabajos);
        }else{
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Debido a que ni el usuario, ni la orden, ni el servicio existen no es posible crearlo");
        }
    }

    @Override
    public List<TrabajosOrdenModel> listarTrabajos() {
        return trabajosOrdenRepository.findAll();
    }

    @Override
    public TrabajosOrdenModel buscarTrabajoPorId(ObjectId trabajoId) {
        Optional<TrabajosOrdenModel> trabajoExistente = trabajosOrdenRepository.findById(trabajoId);
        return trabajoExistente.orElse(null);
    }
}
