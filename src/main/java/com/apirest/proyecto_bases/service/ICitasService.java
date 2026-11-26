package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.CitaModel;

public interface ICitasService {
    public CitaModel crearCita(CitaModel cita);
    public List<CitaModel> listarCitas();
    public CitaModel buscarCitaPorId(ObjectId citaId);
}
