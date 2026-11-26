package com.apirest.proyecto_bases.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apirest.proyecto_bases.model.RepuestosModel;
import com.apirest.proyecto_bases.model.RepuestosPrevistosModel;
import com.apirest.proyecto_bases.model.RepuestosUtilizadosModel;
import com.apirest.proyecto_bases.model.ServicioModel;
import com.apirest.proyecto_bases.model.TrabajosOrdenModel;
import com.apirest.proyecto_bases.repository.ITrabajosOrdenRepository;
import org.springframework.http.HttpStatus;

@Service
public class TrabajosOrdenServiceImp implements ITrabajosOrdenService {
    @Autowired
    ITrabajosOrdenRepository trabajosOrdenRepository;
    @Autowired
    IOrdenServicioService ordenServicioService;
    @Autowired
    IUsuariosService usuariosService;
    @Autowired
    IServiciosService serviciosService;
    @Autowired
    IRepuestosService repuestosService;

    @Override
    public TrabajosOrdenModel crearTrabajoOrden(TrabajosOrdenModel trabajo) {
        validarTrabajoOrden(trabajo);
        return trabajosOrdenRepository.save(trabajo);
    }

    @Override
    public List<TrabajosOrdenModel> listarTrabajosOrden() {
        return trabajosOrdenRepository.findAll();
    }

    @Override
    public TrabajosOrdenModel buscarTrabajoOrdenPorId(ObjectId trabajoId) {
        if (trabajoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de trabajo de orden.");
        }
        return trabajosOrdenRepository.findById(trabajoId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El trabajo de orden con ID " + trabajoId + " no existe."));
    }

    @Override
    public TrabajosOrdenModel actualizarTrabajoOrden(ObjectId trabajoId, TrabajosOrdenModel trabajo) {
        buscarTrabajoOrdenPorId(trabajoId); // valida que exista
        validarTrabajoOrden(trabajo);
        trabajo.setId(trabajoId);
        return trabajosOrdenRepository.save(trabajo);
    }

    @Override
    public void eliminarTrabajoOrden(ObjectId trabajoId) {
        buscarTrabajoOrdenPorId(trabajoId); // valida que exista
        trabajosOrdenRepository.deleteById(trabajoId);
    }

    @Override
    public List<TrabajosOrdenModel> listarTrabajosOrdenPorOrdenId(ObjectId ordenId) {
        return trabajosOrdenRepository.findByOrdenId(ordenId);
    }

    @Override
    public List<TrabajosOrdenModel> listarTrabajosOrdenPorTecnicoId(ObjectId tecnicoId) {
        return trabajosOrdenRepository.findByTecnicoId(tecnicoId);
    }

    // suma mano de obra + repuestos previstos de los trabajos no rechazados de la orden
    @Override
    public Long calcularValorEstimado(ObjectId ordenId) {
        ordenServicioService.buscarOrdenServicioPorId(ordenId);
        List<TrabajosOrdenModel> trabajos = trabajosOrdenRepository.findByOrdenId(ordenId);
        long total = 0;
        for (TrabajosOrdenModel trabajo : trabajos) {
            if (trabajo.getAutorizacion() != null
                    && "rechazado".equalsIgnoreCase(trabajo.getAutorizacion().getDecision())) {
                continue;
            }
            if (trabajo.getManoObraEstimada() != null) {
                total += trabajo.getManoObraEstimada();
            }
            if (trabajo.getRepuestosPrevistos() != null) {
                for (RepuestosPrevistosModel repuesto : trabajo.getRepuestosPrevistos()) {
                    if (repuesto.getCantidad() != null && repuesto.getPrecioUnitarioEstimado() != null) {
                        total += (long) (repuesto.getCantidad() * repuesto.getPrecioUnitarioEstimado());
                    }
                }
            }
        }
        return total;
    }

    // validaciones de campos obligatorios y referencias (fk) hacia otras colecciones
    private void validarTrabajoOrden(TrabajosOrdenModel trabajo) {
        if (trabajo.getOrdenId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo ordenId es obligatorio.");
        }
        if (trabajo.getTecnicoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo tecnicoId es obligatorio.");
        }
        ordenServicioService.buscarOrdenServicioPorId(trabajo.getOrdenId());
        usuariosService.validarRol(trabajo.getTecnicoId(), "tecnico");
        if (trabajo.getServicio() != null) {
            ServicioModel servicio = serviciosService.buscarServicioPorId(trabajo.getServicio().getServicioId());
            trabajo.getServicio().setNombre(servicio.getNombre());
        }
        if (trabajo.getRepuestosPrevistos() != null) {
            for (RepuestosPrevistosModel previsto : trabajo.getRepuestosPrevistos()) {
                RepuestosModel repuesto = repuestosService.buscarRepuestoPorId(previsto.getRepuestoId());
                previsto.setNombre(repuesto.getNombre());
                previsto.setEspecificaciones(repuesto.getEspecificaciones());
                if (previsto.getPrecioUnitarioEstimado() == null) {
                    previsto.setPrecioUnitarioEstimado(repuesto.getPrecio());
                }
            }
        }
        if (trabajo.getEjecucion() != null) {
            if (trabajo.getEjecucion().getRepuestosUtilizados() != null) {
                for (RepuestosUtilizadosModel utilizado : trabajo.getEjecucion().getRepuestosUtilizados()) {
                    repuestosService.buscarRepuestoPorId(utilizado.getRepuestoId());
                }
            }
            // solo los trabajos autorizados por el cliente pueden pasar a ejecución
            if (trabajo.getAutorizacion() == null
                    || !"autorizado".equalsIgnoreCase(trabajo.getAutorizacion().getDecision())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Solo los trabajos autorizados por el cliente pueden pasar a ejecución.");
            }
        }
    }
}
