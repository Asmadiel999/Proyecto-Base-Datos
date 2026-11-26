package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.CitaModel;

public interface ICitasService {
    CitaModel crearCita(CitaModel cita);

    List<CitaModel> listarCitas();

    CitaModel buscarCitaPorId(ObjectId citaId);

    CitaModel actualizarCita(ObjectId citaId, CitaModel cita);

    void eliminarCita(ObjectId citaId);

    List<CitaModel> listarCitasPorClienteId(ObjectId clienteId);

    List<CitaModel> listarCitasPorBicicletaId(ObjectId bicicletaId);

    List<CitaModel> listarCitasPorEstado(String estado);
}