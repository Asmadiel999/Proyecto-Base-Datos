package com.apirest.proyecto_bases.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
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
import org.springframework.stereotype.Service;

import com.apirest.proyecto_bases.model.OrdenServicioModel;

@Service
public class EstadisticaOrdenesPorEstadoServiceImp implements IEstadisticaOrdenesService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Map<String, Object>> ordenesPorEstado(){
        Aggregation agg = newAggregation(
                group("estado").count().as("total"),
                sort(Sort.Direction.DESC, "total"));
        List<Document> docs = mongoTemplate
                .aggregate(agg, mongoTemplate.getCollectionName(OrdenServicioModel.class), Document.class)
                .getMappedResults();

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Document d : docs) {
            Map<String, Object> fila = new LinkedHashMap<>();
            fila.put("estado", d.get("_id"));
            fila.put("total", ((Number) d.get("total")).intValue());
            resultado.add(fila);
        }
        return resultado;
    }
}