package com.grupo04.GestionDeEquiposTEC.Controller;

import com.grupo04.GestionDeEquiposTEC.DTO.LoginRequest;
import com.grupo04.GestionDeEquiposTEC.DTO.UsuarioRequest;
import com.grupo04.GestionDeEquiposTEC.Entidad.Rol;
import com.grupo04.GestionDeEquiposTEC.Entidad.Usuario;
import com.grupo04.GestionDeEquiposTEC.Repository.RolRepository;
import com.grupo04.GestionDeEquiposTEC.Services.RolServicio;
import com.grupo04.GestionDeEquiposTEC.Services.UsuarioServicio;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServicio ususervicio;
    private final RolServicio rolservicio;

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest login) {
        try {

            Optional<Usuario> usua = ususervicio.ingresaUsuario(login.getEmail(), login.getPassword());
            if (usua.isPresent()) {
                Usuario usuario = usua.get();

                // Crear un mapa con los datos (sin password)
                Map<String, Object> response = new HashMap<>();
                response.put("mensaje", "Ingreso correctamente");

                Map<String, Object> datosUsuario = new HashMap<>();
                datosUsuario.put("codigo", usuario.getCodiUsua());
                datosUsuario.put("nombre", usuario.getNombre());
                datosUsuario.put("email", usuario.getEmail());
                datosUsuario.put("rol", usuario.getRol().getNombre());

                response.put("usuario", datosUsuario);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("mensaje", "Credenciales inválidas"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error del sistema"));
        }
    }

    @PostMapping("/addusuario")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioRequest request) {
        try {
            // Buscar el rol en la base de datos
            Rol rol = rolservicio.traerRolForId(request.getCodiRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            // Crear el usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            usuario.setEstado(1);
            usuario.setRol(rol);

            ususervicio.createUsuario(usuario);

            return ResponseEntity.ok().body(Map.of("mensaje", "Agregado Correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al agregar: " + e.getMessage()));
        }
    }

}
