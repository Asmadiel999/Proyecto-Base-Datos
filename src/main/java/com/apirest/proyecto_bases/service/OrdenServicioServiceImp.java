package com.apirest.proyecto_bases.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.BicicletaModel;
import com.apirest.proyecto_bases.model.HallazgosModel;
import com.apirest.proyecto_bases.model.HistorialEstadosModel;
import com.apirest.proyecto_bases.model.OrdenServicioModel;
import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.repository.IOrdenServicioRepository;
import com.apirest.proyecto_bases.repository.ITrabajosOrdenRepository;

@Service
public class OrdenServicioServiceImp implements IOrdenServicioService {
    @Autowired
    IOrdenServicioRepository ordenServicioRepository;
    @Autowired
    IUsuariosService usuariosService;
    @Autowired
    IBicicletaService bicicletaService;
    @Autowired
    ICitasService citasService;
    // se usa el repositorio (y no el servicio) para evitar una dependencia circular
    // con TrabajosOrdenServiceImp
    @Autowired
    ITrabajosOrdenRepository trabajosOrdenRepository;

    private static final List<String> ESTADOS_FINALES = Arrays.asList("entregada", "cancelada");

    @Override
    public OrdenServicioModel crearOrdenServicio(OrdenServicioModel orden) {
        validarOrdenServicio(orden);
        return ordenServicioRepository.save(orden);
    }

    @Override
    public List<OrdenServicioModel> listarOrdenesServicio() {
        return ordenServicioRepository.findAll();
    }

    @Override
    public OrdenServicioModel buscarOrdenServicioPorId(ObjectId ordenId) {
        if (ordenId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de orden de servicio.");
        }
        return ordenServicioRepository.findById(ordenId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "La orden de servicio con ID " + ordenId + " no existe."));
    }

    @Override
    public OrdenServicioModel actualizarOrdenServicio(ObjectId ordenId, OrdenServicioModel orden) {
        buscarOrdenServicioPorId(ordenId); // valida que exista
        orden.setId(ordenId);
        validarOrdenServicio(orden);
        return ordenServicioRepository.save(orden);
    }

    @Override
    public void eliminarOrdenServicio(ObjectId ordenId) {
        buscarOrdenServicioPorId(ordenId); // valida que exista
        ordenServicioRepository.deleteById(ordenId);
    }

    @Override
    public List<OrdenServicioModel> listarOrdenesServicioPorClienteId(ObjectId clienteId) {
        return ordenServicioRepository.findByClienteId(clienteId);
    }

    @Override
    public List<OrdenServicioModel> listarOrdenesServicioPorTecnicosAsignados(ObjectId tecnicoId) {
        return ordenServicioRepository.findByTecnicosAsignados(tecnicoId);
    }

    @Override
    public List<OrdenServicioModel> listarOrdenesServicioPorEstado(String estado) {
        return ordenServicioRepository.findByEstado(estado);
    }

    @Override
    public OrdenServicioModel agregarHistorialEstado(ObjectId ordenId, HistorialEstadosModel historial) {
        OrdenServicioModel orden = buscarOrdenServicioPorId(ordenId);
        if (historial.getEstado() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo estado es obligatorio.");
        }
        validarListaParaEntrega(ordenId, historial.getEstado());
        usuariosService.buscarUsuarioPorId(historial.getUsuarioId());
        if (historial.getFecha() == null) {
            historial.setFecha(new Date());
        }
        if (orden.getHistorialEstados() == null) {
            orden.setHistorialEstados(new ArrayList<>());
        }
        orden.getHistorialEstados().add(historial);
        // el estado actual de la orden es el último del historial
        orden.setEstado(historial.getEstado());
        return ordenServicioRepository.save(orden);
    }

    // una orden solo puede quedar "lista para entrega" si todos sus trabajos
    // autorizados están finalizados y con revisión aprobada
    private void validarListaParaEntrega(ObjectId ordenId, String estado) {
        if (!"lista para entrega".equalsIgnoreCase(estado)) {
            return;
        }
        List<TrabajosOrdenModel> trabajos = trabajosOrdenRepository.findByOrdenId(ordenId);
        for (TrabajosOrdenModel trabajo : trabajos) {
            boolean autorizado = trabajo.getAutorizacion() != null
                    && "autorizado".equalsIgnoreCase(trabajo.getAutorizacion().getDecision());
            if (!autorizado) {
                continue; // los trabajos no autorizados no cuentan
            }
            boolean finalizado = trabajo.getEjecucion() != null
                    && "finalizado".equalsIgnoreCase(trabajo.getEjecucion().getEstado());
            boolean aprobada = trabajo.getRevision() != null && trabajo.getRevision().isAprobada();
            if (!finalizado || !aprobada) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "No se puede marcar la orden como lista para entrega: el trabajo " + trabajo.getId()
                                + " debe estar finalizado y con revisión aprobada.");
            }
        }
    }

    // validaciones de campos obligatorios y referencias (fk) hacia otras colecciones
    private void validarOrdenServicio(OrdenServicioModel orden) {
        if (orden.getClienteId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo clienteId es obligatorio.");
        }
        if (orden.getCoordinadorId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo coordinadorId es obligatorio.");
        }
        if (orden.getBicicleta() == null || orden.getBicicleta().getBicicletaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo bicicleta.bicicletaId es obligatorio.");
        }
        usuariosService.validarRol(orden.getClienteId(), "cliente");
        usuariosService.validarRol(orden.getCoordinadorId(), "coordinador");
        // copia marca y modelo desde la bicicleta registrada (documento embebido)
        BicicletaModel bicicleta = bicicletaService.buscarBicicletaPorId(orden.getBicicleta().getBicicletaId());
        orden.getBicicleta().setMarca(bicicleta.getMarca());
        orden.getBicicleta().setModelo(bicicleta.getModelo());
        if (orden.getCitaId() != null) {
            citasService.buscarCitaPorId(orden.getCitaId());
        }
        if (orden.getTecnicosAsignados() != null) {
            for (ObjectId tecnicoId : orden.getTecnicosAsignados()) {
                usuariosService.validarRol(tecnicoId, "tecnico");
            }
        }
        if (orden.getDiagnostico() != null && orden.getDiagnostico().getHallazgos() != null) {
            for (HallazgosModel hallazgo : orden.getDiagnostico().getHallazgos()) {
                usuariosService.validarRol(hallazgo.getTecnicoId(), "tecnico");
            }
        }
        if (orden.getHistorialEstados() != null) {
            for (HistorialEstadosModel historial : orden.getHistorialEstados()) {
                usuariosService.buscarUsuarioPorId(historial.getUsuarioId());
            }
        }
        // una bicicleta solo puede tener una orden activa a la vez
        List<OrdenServicioModel> activas = ordenServicioRepository
                .findByBicicleta_BicicletaIdAndEstadoNotIn(orden.getBicicleta().getBicicletaId(), ESTADOS_FINALES);
        for (OrdenServicioModel otra : activas) {
            if (!otra.getId().equals(orden.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "La bicicleta ya tiene una orden de servicio activa.");
            }
        }
        validarListaParaEntrega(orden.getId(), orden.getEstado());
        if (orden.getValoracionServicio() != null && !"entregada".equalsIgnoreCase(orden.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se pueden valorar órdenes entregadas.");
        }
    }
}