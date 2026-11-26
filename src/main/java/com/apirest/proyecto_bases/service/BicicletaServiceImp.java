package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.BicicletaModel;

import com.apirest.proyecto_bases.repository.IBicicletaRepository;

@Service 

public class BicicletaServiceImp implements IBicicletaService{
    @Autowired IBicicletaRepository bicicletaRepository; 
    @Autowired IUsuariosService usuariosService; 

    @Override
    public BicicletaModel crearBicicleta(BicicletaModel bicicleta) {
        return bicicletaRepository.save(bicicleta);
    }

    @Override
    public List<BicicletaModel> listarBicicletas() {
        return bicicletaRepository.findAll();
    }

    @Override
    public BicicletaModel buscarBicicletaPorId(ObjectId idBicicleta) {
        if (idBicicleta == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de bicicleta.");
        }
        Optional<BicicletaModel> bicicletaExistente = bicicletaRepository.findById(idBicicleta);
        if (bicicletaExistente.isPresent()) {
            return bicicletaExistente.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La bicicleta con ID " + idBicicleta + " no existe.");
        }
    }

    @Override
    public BicicletaModel actualizarBicicleta(ObjectId id, BicicletaModel datosActualizados) {
        buscarBicicletaPorId(id);
        datosActualizados.setId(id);
        return bicicletaRepository.save(datosActualizados); 
    }

    @Override
    public List<BicicletaModel> listarBicicletasPorClienteId(ObjectId id) {
        if (id == null) {

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El clienteId no puede ser nulo");
        }

        return bicicletaRepository.findByClienteId(id);
    }

    @Override
    public void eliminarBicicleta(ObjectId id) {
        buscarBicicletaPorId(id);
        bicicletaRepository.deleteById(id);
    }


}
