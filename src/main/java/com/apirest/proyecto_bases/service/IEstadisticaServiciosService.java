package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Map;

public interface IEstadisticaServiciosService {
    List<Map<String, Object>> serviciosMasSolicitados(Integer limite);
}