package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.BicicletaModel;
import com.apirest.proyecto_bases.model.CitaModel;
import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.repository.ICitasRepository;

@Service 
public class CitasServiceImp implements ICitasService{
    @Autowired ICitasRepository citasRepository;
    @Autowired IUsuariosService usuariosService;
    @Autowired IBicicletaService bicicletaService;
    @Autowired IServiciosService serviciosService;

    @Override
    public CitaModel crearCita(CitaModel cita) {
        ObjectId usuarioId = cita.getClienteId();
        ObjectId bicicletaId = cita.getBicicletaId();
        UsuarioModel usuario = usuariosService.buscarUsuarioPorId(usuarioId);
        BicicletaModel bicicleta = bicicletaService.buscarBicicletaPorId(bicicletaId);

        if (usuario != null && bicicleta != null) {
            return citasRepository.save(cita); 
        }else{
             throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "El usuario y la bicicleta no existen por lo tanto no es posible crear la cita");
        }
    }

    @Override
    public List<CitaModel> listarCitas() {
        return citasRepository.findAll(); 
    }

    @Override
    public CitaModel buscarCitaPorId(ObjectId citaId) {
        Optional<CitaModel> citaExistente = citasRepository.findById(citaId);
        return citaExistente.orElse(null); 
    }
}
