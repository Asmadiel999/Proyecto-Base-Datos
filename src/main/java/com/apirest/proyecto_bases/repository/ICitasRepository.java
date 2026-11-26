package com.apirest.proyecto_bases.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.proyecto_bases.model.CitaModel;
import java.util.Date;
import java.util.List;

public interface ICitasRepository extends MongoRepository<CitaModel, ObjectId> {
    boolean existsByBicicletaIdAndFechaHoraAndEstadoIn(ObjectId bicicletaId, Date fechaHora, List<String> estados);
} //No puede existir otra cita en la misma fecha y hora, ni para la misma bicicleta
