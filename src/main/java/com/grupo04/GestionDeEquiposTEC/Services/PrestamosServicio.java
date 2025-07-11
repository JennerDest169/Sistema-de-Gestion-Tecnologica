package com.grupo04.GestionDeEquiposTEC.Services;

import com.grupo04.GestionDeEquiposTEC.Entidad.Prestamos;
import com.grupo04.GestionDeEquiposTEC.Repository.PrestamosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamosServicio {
    private final PrestamosRepository presta;

    public List<Prestamos> allPrestamos(){
        return presta.findAll();
    }

    public void crearPrestamos(Prestamos prestamos){
        presta.save(prestamos);
    }

    public void actualizarPrestamos(Prestamos prestamos){
        presta.save(prestamos);
    }
}
