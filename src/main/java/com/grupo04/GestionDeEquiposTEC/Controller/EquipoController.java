package com.grupo04.GestionDeEquiposTEC.Controller;

import com.grupo04.GestionDeEquiposTEC.DTO.EquipoRequest;
import com.grupo04.GestionDeEquiposTEC.DTO.EquipoUpdateRequest;
import com.grupo04.GestionDeEquiposTEC.Entidad.Equipos;
import com.grupo04.GestionDeEquiposTEC.Services.EquipoServicio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoServicio equiservi;

    @PostMapping("/allequipos")
    public ResponseEntity<?> findAllEquipos() {
        try {
            List<Equipos> todo = equiservi.allEquipos();

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Obteniste todos los equipos");
            List<Map<String, Object>> contenido = new ArrayList<>();
            for (Equipos eq : todo) {
                Map<String, Object> item = new HashMap<>();
                item.put("codigo", eq.getCodiEquipo());
                item.put("nombre", eq.getNombre());
                item.put("descripcion", eq.getDescripcion());
                item.put("serie", eq.getSerie());
                item.put("estado", eq.getEstado());
                contenido.add(item);
            }
            respuesta.put("equipos", contenido);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error del sistema"));
        }
    }

    @PostMapping("/addequipo")
    public ResponseEntity<?> crearEquipo(@RequestBody EquipoRequest respuesta) {
        try {
            Equipos eq = new Equipos();
            eq.setNombre(respuesta.getNombre());
            eq.setDescripcion(respuesta.getDescripcion());
            eq.setSerie(respuesta.getSerie());
            eq.setEstado((short) 1);

            equiservi.crearEquipos(eq);

            return ResponseEntity.ok().body(Map.of("mensaje", "Agregado Correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al agregar: " + e.getMessage()));
        }
    }

    @PostMapping("/oneequipo")
    public ResponseEntity<?> obtenerUnEquipo(@RequestParam("id") Integer id) {
        try {
            Optional<Equipos> datos = equiservi.findOneEquipo(id);
            if (datos.isPresent()) {
                Equipos eq = datos.get();
                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("mensaje", "Obteniste datos del equipo");
                Map<String, Object> item = new HashMap<>();
                item.put("codigo", eq.getCodiEquipo());
                item.put("nombre", eq.getNombre());
                item.put("descripcion", eq.getDescripcion());
                item.put("serie", eq.getSerie());
                respuesta.put("equipo", item);
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("mensaje", "No ay ese equipo con ese ID"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error del sistema"));
        }
    }

    @PutMapping("/updateequipo")
    public ResponseEntity<?> actualizarEquipo(@RequestBody EquipoUpdateRequest euRes) {
        try {
            Optional<Equipos> datos = equiservi.findOneEquipo(euRes.getCodiEquipo());
            Equipos eq = datos.get();
            Equipos eqs = new Equipos();
            eqs.setCodiEquipo(euRes.getCodiEquipo());
            eqs.setNombre(euRes.getNombre());
            eqs.setDescripcion(euRes.getDescripcion());
            eqs.setSerie(euRes.getSerie());
            eqs.setEstado(eq.getEstado());
            equiservi.actualizarEquipo(eqs);

            return ResponseEntity.ok().body(Map.of("mensaje", "Editado Correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al editar: " + e.getMessage()));
        }
    }

    @PutMapping("/deleteequipo")
    public ResponseEntity<?> eliminarEquipo(@RequestParam("id") Integer id) {
        try {
            Optional<Equipos> datos = equiservi.findOneEquipo(id);

            if (datos.isPresent()) {

                Equipos eq = datos.get();
                eq.setEstado((short) 0);
                equiservi.actualizarEquipo(eq);

                return ResponseEntity.ok().body(Map.of("mensaje", "Eliminado Correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("mensaje", "No ay ese equipo con ese ID"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error del sistema"));
        }
    }
}
