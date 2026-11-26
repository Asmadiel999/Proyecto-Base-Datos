package com.apirest.proyecto_bases.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.BicicletaModel;
import com.apirest.proyecto_bases.model.CitaModel;
import com.apirest.proyecto_bases.model.HistorialEstadosModel;
import com.apirest.proyecto_bases.model.OrdenServicioModel;
import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.model.UsuarioModel;
import com.apirest.proyecto_bases.model.ValoracionServicioModel;
import com.apirest.proyecto_bases.repository.IOrdenServicioRepository;
import com.apirest.proyecto_bases.repository.ITrabajosOrdenRepository;

@Service 
public class OrdenServicioServiceImp implements IOrdenServicioService {
    @Autowired IOrdenServicioRepository ordenServicioRepository;
    @Autowired IUsuariosService usuariosService;
    @Autowired IBicicletaService bicicletaService;
    @Autowired ICitasService citasService;
    @Autowired ITrabajosOrdenRepository trabajosOrdenRepository;

    @Override
    public OrdenServicioModel crearOrdenServicio(OrdenServicioModel orden) {
        ObjectId usuarioId = orden.getClienteId();
        ObjectId bicicletaId = orden.getBicicleta().getBicicletaId(); 
        ObjectId citaId = orden.getCitaId(); 

        UsuarioModel usuario = usuariosService.buscarUsuarioPorId(usuarioId);
        BicicletaModel bicicleta = bicicletaService.buscarBicicletaPorId(bicicletaId);
        
        CitaModel cita = (citaId != null) ? citasService.buscarCitaPorId(citaId) : null;

        if (usuario != null && bicicleta != null && (citaId == null || cita != null)) {
            
            List<String> estadosActivos = List.of(
                "recibida", "en diagnostico", "pendiente de autorizacion", "en ejecucion", "lista para entrega"
            );
            if (ordenServicioRepository.existsByBicicleta_BicicletaIdAndEstadoIn(bicicletaId, estadosActivos)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, 
                    "La bicicleta ya tiene una orden de servicio activa");
            }

            if (orden.getHistorialEstados() == null || orden.getHistorialEstados().isEmpty()) {
                HistorialEstadosModel registroInicial = new HistorialEstadosModel();
                registroInicial.setEstado(orden.getEstado() != null ? orden.getEstado() : "recibida");
                registroInicial.setFecha(orden.getFechaRecepcion() != null ? orden.getFechaRecepcion() : new Date());
                registroInicial.setUsuarioId(orden.getCoordinadorId());
                registroInicial.setObservacion("Recepción inicial de la bicicleta");
                orden.setHistorialEstados(new ArrayList<>(List.of(registroInicial)));
            }

            return ordenServicioRepository.save(orden);            
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "El usuario, la bicicleta o la cita indicada no existen, por ende no se puede generar la orden");
        }
    }

    @Override
    public OrdenServicioModel actualizarEstado(ObjectId ordenId, String nuevoEstado, ObjectId usuarioId, String observacion) {
        OrdenServicioModel orden = buscarOrdenServicioPorId(ordenId);
        if (orden == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de servicio no existe");
        }

        UsuarioModel usuario = usuariosService.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario responsable del cambio no existe");
        }

        if (observacion == null || observacion.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe registrar una observación para el cambio de estado");
        }

        if ("lista para entrega".equalsIgnoreCase(nuevoEstado)) {
            List<TrabajosOrdenModel> trabajos = trabajosOrdenRepository.findByOrdenId(ordenId);
            for (TrabajosOrdenModel t : trabajos) {
                if (t.getAutorizacion() != null && "autorizado".equalsIgnoreCase(t.getAutorizacion().getDecision())) {
                    
                    if (t.getEjecucion() == null || !"finalizado".equalsIgnoreCase(t.getEjecucion().getEstado())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "No se puede marcar como 'lista para entrega': hay trabajos autorizados sin finalizar");
                    }

                    if (t.getRevision() == null || !t.getRevision().isAprobada()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No se puede marcar como 'lista para entrega' ya que hay trabajos sin revisión aprobada");}
                }
            }
        }


        orden.setEstado(nuevoEstado);


        if (orden.getHistorialEstados() == null) {
            orden.setHistorialEstados(new ArrayList<>());
        }
        HistorialEstadosModel cambio = new HistorialEstadosModel();
        cambio.setEstado(nuevoEstado);
        cambio.setFecha(new Date());
        cambio.setUsuarioId(usuarioId);
        cambio.setObservacion(observacion);

        orden.getHistorialEstados().add(cambio);

        return ordenServicioRepository.save(orden);
    }

    @Override
    public OrdenServicioModel registrarValoracion(ObjectId ordenId, ObjectId clienteId, ValoracionServicioModel valoracion) {
        OrdenServicioModel orden = buscarOrdenServicioPorId(ordenId);
        if (orden == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden de servicio no existe");
        }

        if (!"entregada".equalsIgnoreCase(orden.getEstado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Solo se puede valorar una orden que se encuentre en estado 'entregada'");
        }

        if (orden.getClienteId() == null || !orden.getClienteId().equals(clienteId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, 
                "Solo el cliente propietario de la orden puede registrar la valoración");
        }

        if (orden.getValoracionServicio() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                "Esta orden de servicio ya cuenta con una valoración registrada");
        }

        orden.setValoracionServicio(valoracion);
        return ordenServicioRepository.save(orden);
    }

    @Override
    public List<OrdenServicioModel> listarOrdenesServicios() {
        return ordenServicioRepository.findAll();
    }

    @Override
    public OrdenServicioModel buscarOrdenServicioPorId(ObjectId ordenServicioId) {
        Optional<OrdenServicioModel> ordenServicioExistente = ordenServicioRepository.findById(ordenServicioId);
        return ordenServicioExistente.orElse(null); 
    }
}