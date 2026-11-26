package com.apirest.proyecto_bases.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.apirest.proyecto_bases.model.OrdenServicioModel;

@Service
public class EstadisticaValoracionesServiceImp implements IEstadisticaValoracionesService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Map<String, Object> resumenValoraciones() {
        String coleccion = mongoTemplate.getCollectionName(OrdenServicioModel.class);
        Criteria conValoracion = Criteria.where("valoracionServicio.calificacion").ne(null);

        Aggregation resumen = newAggregation(
                match(conValoracion),
                group().avg("valoracionServicio.calificacion").as("promedio")
                        .min("valoracionServicio.calificacion").as("minima")
                        .max("valoracionServicio.calificacion").as("maxima")
                        .count().as("total"));
        List<Document> r = mongoTemplate.aggregate(resumen, coleccion, Document.class).getMappedResults();

        Aggregation porNota = newAggregation(
                match(conValoracion),
                group("valoracionServicio.calificacion").count().as("cantidad"));
        List<Document> n = mongoTemplate.aggregate(porNota, coleccion, Document.class).getMappedResults();

        Map<Integer, Integer> distribucion = new TreeMap<>(); // TreeMap las deja ordenadas por nota
        for (Document d : n) {
            distribucion.put(((Number) d.get("_id")).intValue(), ((Number) d.get("cantidad")).intValue());
        }

        Map<String, Object> resultado = new LinkedHashMap<>();
        if (r.isEmpty()) {
            resultado.put("totalValoraciones", 0);
            resultado.put("promedio", null);
        } else {
            Document d = r.get(0);
            resultado.put("totalValoraciones", ((Number) d.get("total")).intValue());
            resultado.put("promedio", Math.round(((Number) d.get("promedio")).doubleValue() * 100.0) / 100.0);
            resultado.put("minima", d.get("minima"));
            resultado.put("maxima", d.get("maxima"));
        }
        resultado.put("distribucionPorCalificacion", distribucion);
        return resultado;
    }
}