package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.proyecto_bases.model.RepuestosModel;

public interface IRepuestosService {
    public RepuestosModel crearRepuesto(RepuestosModel repuesto);
    public List<RepuestosModel> listarRepuestos(); 
    public RepuestosModel buscarRepuestosPorId(ObjectId repuestoId); 
}
