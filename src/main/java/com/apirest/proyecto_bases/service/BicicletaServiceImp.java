package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.BicicletaModel;
import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.repository.IBicicletaRepository;

@Service 

public class BicicletaServiceImp implements IBicicletaService{
    @Autowired IBicicletaRepository bicicletaRepository; 
    @Autowired IUsuariosService usuariosService; 

    @Override
    public BicicletaModel crearBicicleta(BicicletaModel bicicleta) {
        ObjectId usuarioId = bicicleta.getClienteId();
        UsuarioModel usuario = usuariosService.buscarUsuarioPorId(usuarioId);

        if(usuario != null){
            return bicicletaRepository.save(bicicleta);
        }else{
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "El usuario no existe, así que no se puede crear la colección");
        }
    }

    @Override
    public List<BicicletaModel> listarBicicletas() {
        return bicicletaRepository.findAll();
    }

    @Override
    public BicicletaModel buscarBicicletaPorId(ObjectId idBicicleta) {
        Optional<BicicletaModel> bicicletaExistente = bicicletaRepository.findById(idBicicleta);
        return bicicletaExistente.orElse(null);
    }
}
