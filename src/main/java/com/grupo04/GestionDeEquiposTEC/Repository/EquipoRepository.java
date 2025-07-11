package com.grupo04.GestionDeEquiposTEC.Repository;
import com.grupo04.GestionDeEquiposTEC.Entidad.Equipos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepository extends JpaRepository<Equipos, Integer>{
    
}
