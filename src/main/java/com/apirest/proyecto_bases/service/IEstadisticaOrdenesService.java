package com.apirest.proyecto_bases.service;

import java.util.List;
import java.util.Map;

public interface IEstadisticaOrdenesService {
    List<Map<String, Object>> ordenesPorEstado();
}