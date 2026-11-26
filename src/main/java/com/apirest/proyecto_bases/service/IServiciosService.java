package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.ServicioModel;

public interface IServiciosService {
    public ServicioModel crearServicio(ServicioModel servicio);
    public List<ServicioModel> listarUsuario();
    public ServicioModel buscarServicioPorId(ObjectId servicioId); 
}
