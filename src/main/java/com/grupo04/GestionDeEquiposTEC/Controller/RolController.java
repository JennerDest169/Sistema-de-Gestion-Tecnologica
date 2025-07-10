package com.grupo04.GestionDeEquiposTEC.Controller;

import com.grupo04.GestionDeEquiposTEC.Entidad.Rol;
import com.grupo04.GestionDeEquiposTEC.Services.RolServicio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RolController {
    private final RolServicio rolservicio;
    
    @GetMapping("/allrol")
    public ResponseEntity<?> allFindRol(){
        try {
            List<Rol> datos = rolservicio.traerTodoRol();
            Map<String, Object> respu = new HashMap<>();
            respu.put("mensaje", "Todos los roles obtenidos");
            List<Map<String, Object>> roles = new ArrayList<>();
            for (Rol role : datos) {
                Map<String, Object> item = new HashMap<>();
                item.put("codigo", role.getCodiRol());
                item.put("nombre", role.getNombre());
                roles.add(item);
            }
            respu.put("roles", roles);
            return ResponseEntity.ok(respu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error del sistema"));
        }
    }
}
