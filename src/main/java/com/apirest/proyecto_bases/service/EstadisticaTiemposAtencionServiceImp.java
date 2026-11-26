package com.apirest.proyecto_bases.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.apirest.proyecto_bases.model.OrdenServicioModel;

@Service
public class EstadisticaTiemposAtencionServiceImp implements IEstadisticaTiemposService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Map<String, Object> tiemposDeAtencion() {
        String coleccion = mongoTemplate.getCollectionName(OrdenServicioModel.class);

        // total de órdenes entregadas, incluidas las que no se pueden medir
        long totalEntregadas = mongoTemplate.count(
                new Query(Criteria.where("estado").is("entregada")), coleccion);

        Aggregation agg = newAggregation(
                // 1. solo órdenes entregadas
                match(Criteria.where("estado").is("entregada")),
                // 2. una fila por cada cambio de estado del historial
                unwind("historialEstados"),
                // 3. nos quedamos con la fila donde la orden pasó a "entregada"
                match(Criteria.where("historialEstados.estado").regex("^entregada$", "i")),
                // 4. una fila por orden, con la fecha de entrega más reciente
                group("_id")
                        .max("historialEstados.fecha").as("fechaEntrega")
                        .first("fechaRecepcion").as("fechaRecepcion")
                        .first("fechaEntregaEstimada").as("fechaEstimada"),
                // 5. descartamos las que no tienen ambas fechas
                match(new Criteria().andOperator(
                        Criteria.where("fechaEntrega").ne(null),
                        Criteria.where("fechaRecepcion").ne(null))),
                // 6. horas de atención de cada orden y si se entregó a tiempo (1 o 0)
                project()
                        .and(ArithmeticOperators.Divide.valueOf(
                                ArithmeticOperators.Subtract.valueOf("fechaEntrega").subtract("fechaRecepcion"))
                                .divideBy(3_600_000)).as("horas")
                        .and(ConditionalOperators.when(
                                ComparisonOperators.valueOf("fechaEntrega").lessThanEqualTo("fechaEstimada"))
                                .then(1).otherwise(0)).as("aTiempo"),
                // 7. estadísticas de todas las órdenes juntas
                group()
                        .avg("horas").as("promedio")
                        .min("horas").as("minimo")
                        .max("horas").as("maximo")
                        .sum("aTiempo").as("entregadasATiempo")
                        .count().as("ordenesMedidas"));
        List<Document> r = mongoTemplate.aggregate(agg, coleccion, Document.class).getMappedResults();

        Map<String, Object> resultado = new LinkedHashMap<>();
        int medidas = r.isEmpty() ? 0 : ((Number) r.get(0).get("ordenesMedidas")).intValue();
        resultado.put("ordenesEntregadas", totalEntregadas);
        resultado.put("ordenesMedidas", medidas);
        resultado.put("ordenesSinDatos", totalEntregadas - medidas);

        if (r.isEmpty()) {
            resultado.put("entregadasATiempo", 0);
            resultado.put("promedioHoras", null);
            resultado.put("minimoHoras", null);
            resultado.put("maximoHoras", null);
            return resultado;
        }

        Document d = r.get(0);
        resultado.put("entregadasATiempo", ((Number) d.get("entregadasATiempo")).intValue());
        resultado.put("promedioHoras", redondear(((Number) d.get("promedio")).doubleValue()));
        resultado.put("minimoHoras", redondear(((Number) d.get("minimo")).doubleValue()));
        resultado.put("maximoHoras", redondear(((Number) d.get("maximo")).doubleValue()));
        return resultado;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}