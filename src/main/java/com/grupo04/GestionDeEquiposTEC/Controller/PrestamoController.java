package com.grupo04.GestionDeEquiposTEC.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.grupo04.GestionDeEquiposTEC.DTO.PrestamoUpdateRequest;
import com.grupo04.GestionDeEquiposTEC.Entidad.Prestamos;
import com.grupo04.GestionDeEquiposTEC.Services.PrestamoServicio;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans") // Cambia esto a "/loans" si quieres usar /loans/approve
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoServicio prestamoServicio;

    /**
     * Aprobar o cambiar estado de un préstamo
     * @param req Datos del préstamo a actualizar
     * @return ResponseEntity con resultado de la operación
     */
    @PutMapping("/approve")
    public ResponseEntity<?> aprobarPrestamo(@RequestBody PrestamoUpdateRequest req) {
        try {
            // Validaciones de entrada
            if (req.getIdPrestamo() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("ID del préstamo es requerido"));
            }

            Optional<Prestamos> datos = prestamoServicio.findPrestamoById(req.getIdPrestamo());

            if (datos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("No existe préstamo con ID: " + req.getIdPrestamo()));
            }

            Prestamos prestamo = datos.get();

            // Validar si el estado ya es el mismo
            if (prestamo.getEstado() == req.getNuevoEstado()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("El préstamo ya está en ese estado"));
            }

            // Guardar estado anterior para la respuesta
            short estadoAnterior = prestamo.getEstado();
            
            // Actualizar estado
            prestamo.setEstado(req.getNuevoEstado());
            prestamoServicio.updatePrestamo(prestamo);

            // Respuesta exitosa
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("mensaje", "Estado actualizado correctamente");
            response.put("prestamo_id", prestamo.getCodiPrest());
            response.put("estado_anterior", estadoAnterior);
            response.put("nuevo_estado", prestamo.getEstado());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error del sistema: " + e.getMessage()));
        }
    }

    /**
     * Obtener préstamos por usuario
     * @param idUsuario ID del usuario
     * @return Lista de préstamos del usuario
     */
    @GetMapping("/user/{idUsuario}")
    public ResponseEntity<?> prestamosPorUsuario(@PathVariable Integer idUsuario) {
        try {
            if (idUsuario == null || idUsuario <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("ID de usuario inválido"));
            }

            List<Prestamos> lista = prestamoServicio.findPrestamosByUsuario(idUsuario);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("total", lista.size());
            response.put("prestamos", lista);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al obtener préstamos: " + e.getMessage()));
        }
    }

    /**
     * Obtener préstamos por equipo
     * @param idEquipo ID del equipo
     * @return Lista de préstamos del equipo
     */
    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<?> prestamosPorEquipo(@PathVariable Integer idEquipo) {
        try {
            if (idEquipo == null || idEquipo <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("ID de equipo inválido"));
            }

            List<Prestamos> lista = prestamoServicio.findPrestamosByEquipo(idEquipo);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("total", lista.size());
            response.put("prestamos", lista);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al obtener préstamos: " + e.getMessage()));
        }
    }

    /**
     * Obtener préstamos por estado
     * @param estado Estado del préstamo
     * @return Lista de préstamos con el estado especificado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> prestamosPorEstado(@PathVariable short estado) {
        try {
            List<Prestamos> lista = prestamoServicio.findPrestamosByEstado(estado);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("estado_consultado", estado);
            response.put("total", lista.size());
            response.put("prestamos", lista);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al obtener préstamos: " + e.getMessage()));
        }
    }

    /**
     * Obtener todos los préstamos
     * @return Lista de todos los préstamos
     */
    @GetMapping("/all")
    public ResponseEntity<?> obtenerTodosLosPrestamos() {
        try {
            List<Prestamos> lista = prestamoServicio.findAllPrestamos();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("total", lista.size());
            response.put("prestamos", lista);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al obtener préstamos: " + e.getMessage()));
        }
    }

    /**
     * Obtener préstamo por ID
     * @param id ID del préstamo
     * @return Datos del préstamo
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPrestamoPorId(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("ID de préstamo inválido"));
            }

            Optional<Prestamos> prestamo = prestamoServicio.findPrestamoById(id);
            
            if (prestamo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Préstamo no encontrado con ID: " + id));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("prestamo", prestamo.get());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al obtener préstamo: " + e.getMessage()));
        }
    }

    /**
     * Crear respuesta de error consistente
     * @param mensaje Mensaje de error
     * @return Map con estructura de error
     */
    private Map<String, Object> createErrorResponse(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("mensaje", mensaje);
        return response;
    }
}