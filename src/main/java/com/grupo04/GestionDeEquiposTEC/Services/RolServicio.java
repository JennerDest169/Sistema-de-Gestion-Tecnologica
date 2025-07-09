package com.grupo04.GestionDeEquiposTEC.Services;

import com.grupo04.GestionDeEquiposTEC.Entidad.Rol;
import com.grupo04.GestionDeEquiposTEC.Repository.RolRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolServicio {
    
    private final RolRepository rolRepo;
    
    public Optional<Rol> traerRolForId(Integer id){
        return rolRepo.findById(id);
    }
    
}
