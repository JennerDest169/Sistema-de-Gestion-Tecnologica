package com.grupo04.GestionDeEquiposTEC.Services;

import com.grupo04.GestionDeEquiposTEC.Entidad.Equipos;
import com.grupo04.GestionDeEquiposTEC.Repository.EquipoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipoServicio {
    
    private final EquipoRepository equirepo;
    
    public List<Equipos> allEquipos(){
        return equirepo.findAll();
    }
    
    public void crearEquipos(Equipos eq){
        equirepo.save(eq);
    } 
    
    public Optional<Equipos> findOneEquipo(int id){
        return equirepo.findById(id);
    }
    
    public void actualizarEquipo(Equipos eqa){
        equirepo.save(eqa);
    }
}
