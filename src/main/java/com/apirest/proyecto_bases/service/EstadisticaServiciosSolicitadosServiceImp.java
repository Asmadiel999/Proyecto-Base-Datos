package com.apirest.proyecto_bases.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.TrabajosOrdenModel;

@Service
public class EstadisticaServiciosSolicitadosServiceImp implements IEstadisticaServiciosService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Map<String, Object>> serviciosMasSolicitados(Integer limite) {
        if (limite != null && limite < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El límite debe ser mayor a 0.");
        }

        List<AggregationOperation> etapas = new ArrayList<>();
        etapas.add(match(Criteria.where("servicio.servicioId").ne(null)));
        etapas.add(group("servicio.servicioId")
                .first("servicio.nombre").as("nombre")
                .count().as("totalSolicitudes"));
        etapas.add(sort(Sort.Direction.DESC, "totalSolicitudes"));
        if (limite != null) {
            etapas.add(limit(limite));
        }

        Aggregation agg = newAggregation(etapas);
        List<Document> docs = mongoTemplate
                .aggregate(agg, mongoTemplate.getCollectionName(TrabajosOrdenModel.class), Document.class)
                .getMappedResults();

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Document d : docs) {
            Map<String, Object> fila = new LinkedHashMap<>();
            fila.put("servicioId", String.valueOf(d.get("_id")));
            fila.put("nombre", d.get("nombre"));
            fila.put("totalSolicitudes", ((Number) d.get("totalSolicitudes")).intValue());
            resultado.add(fila);
        }
        return resultado;
    }
}