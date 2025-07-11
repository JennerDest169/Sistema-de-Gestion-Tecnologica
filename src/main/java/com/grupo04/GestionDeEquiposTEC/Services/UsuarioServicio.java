package com.grupo04.GestionDeEquiposTEC.Services;

import com.grupo04.GestionDeEquiposTEC.Entidad.Usuario;
import com.grupo04.GestionDeEquiposTEC.Repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    @Autowired
    private PasswordEncoder passEnco;
    private final UsuarioRepository usuRepo;

    public Optional<Usuario> ingresaUsuario(String email, String pass) {
        Optional<Usuario> usuarioOp = usuRepo.findByEmail(email);
        if (usuarioOp.isPresent()) {
            Usuario us = usuarioOp.get();
            if (passEnco.matches(pass, us.getPassword())) {
                return Optional.of(us);
            }
        }

        return Optional.empty();
    }

    public void createUsuario(Usuario usu) {
        if (usu.getRol() == null || usu.getRol().getCodiRol() == null) {
            throw new IllegalArgumentException("El usuario debe tener un rol asignado");
        }
        usu.setPassword(passEnco.encode(usu.getPassword()));
        usuRepo.save(usu);
    }
    
    public List<Usuario> todoUsuarios(){
        return usuRepo.findAll();
    }
    
    public Optional<Usuario> usuarioForId(int id){
        return usuRepo.findById(id);
    }
    
    public void updateUsuario(Usuario usu){
        usuRepo.save(usu);
    }
}
