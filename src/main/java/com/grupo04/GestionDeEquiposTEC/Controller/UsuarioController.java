package com.grupo04.GestionDeEquiposTEC.Controller;

import com.grupo04.GestionDeEquiposTEC.DTO.LoginRequest;
import com.grupo04.GestionDeEquiposTEC.DTO.UsuarioRequest;
import com.grupo04.GestionDeEquiposTEC.DTO.UsuarioUpdateRequest;
import com.grupo04.GestionDeEquiposTEC.Entidad.Rol;
import com.grupo04.GestionDeEquiposTEC.Entidad.Usuario;
import com.grupo04.GestionDeEquiposTEC.Repository.RolRepository;
import com.grupo04.GestionDeEquiposTEC.Services.RolServicio;
import com.grupo04.GestionDeEquiposTEC.Services.UsuarioServicio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            Rol rol = rolservicio.traerRolForId(request.getCodiRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

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

    @PostMapping("/allusuarios")
    public ResponseEntity<?> allUsuario() {
        try {
            List<Usuario> usu = ususervicio.todoUsuarios();
            Map<String, Object> respon = new HashMap<>();
            respon.put("mensaje", "Obtuvo todos los usuarios correctamente");

            List<Map<String, Object>> olgf = new ArrayList<>();
            for (Usuario usuario : usu) {
                Map<String, Object> datosUsuarios = new HashMap<>();
                datosUsuarios.put("codigo", usuario.getCodiUsua());
                datosUsuarios.put("nombre", usuario.getNombre());
                datosUsuarios.put("email", usuario.getEmail());
                datosUsuarios.put("rol", usuario.getRol().getNombre());
                datosUsuarios.put("estado", usuario.getEstado());
                olgf.add(datosUsuarios);
            }
            respon.put("usuarios",olgf);
            return ResponseEntity.ok(respon);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error del sistema"));
        }
    }

    @PostMapping("/oneusuario")
    public ResponseEntity<?> findUsuarioForId(@RequestParam("id") Integer id) {
        try {
            Optional<Usuario> rspta = ususervicio.usuarioForId(id);

            if (rspta.isPresent()) {
                Usuario usuario = rspta.get();

                // Crear un mapa con los datos (sin password)
                Map<String, Object> response = new HashMap<>();
                response.put("mensaje", "Obtuvo al usuario correctamente");

                Map<String, Object> datosUsuario = new HashMap<>();
                datosUsuario.put("codigo", usuario.getCodiUsua());
                datosUsuario.put("nombre", usuario.getNombre());
                datosUsuario.put("email", usuario.getEmail());
                datosUsuario.put("rol", usuario.getRol().getCodiRol());

                response.put("usuario", datosUsuario);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("mensaje", "No ay ese usuario con ese ID"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error del sistema"));
        }
    }

    @PutMapping("/updateusuario")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioUpdateRequest uuRes) {
        try {
            Rol rol = rolservicio.traerRolForId(uuRes.getCodiRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            Optional<Usuario> rspta = ususervicio.usuarioForId(uuRes.getCodiUsua());
            Usuario usuario = rspta.get();
            Usuario usu = new Usuario();
            usu.setCodiUsua(uuRes.getCodiUsua());
            usu.setNombre(uuRes.getNombre());
            usu.setEmail(uuRes.getEmail());
            usu.setPassword(usuario.getPassword());
            usu.setRol(rol);
            usu.setEstado(usuario.getEstado());

            ususervicio.updateUsuario(usu);

            return ResponseEntity.ok().body(Map.of("mensaje", "Editado Correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al agregar: " + e.getMessage()));
        }
    }

    @PutMapping("/deleteusuario")
    public ResponseEntity<?> deleteUsuario(@RequestParam("id") Integer id) {
        try {
            Optional<Usuario> rspta = ususervicio.usuarioForId(id);

            if (rspta.isPresent()) {
                Usuario usuario = rspta.get();
                usuario.setEstado(0);

                ususervicio.updateUsuario(usuario);

                return ResponseEntity.ok().body(Map.of("mensaje", "Eliminado Correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("mensaje", "No ay ese usuario con ese ID"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error del sistema"));
        }
    }
}
