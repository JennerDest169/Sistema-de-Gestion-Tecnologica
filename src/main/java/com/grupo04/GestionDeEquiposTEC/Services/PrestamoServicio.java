package com.grupo04.GestionDeEquiposTEC.Services;

import com.grupo04.GestionDeEquiposTEC.Entidad.Prestamos;
import com.grupo04.GestionDeEquiposTEC.Repository.PrestamoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrestamoServicio {
    
    private final PrestamoRepository prestamoRepo;

    public Optional<Prestamos> findPrestamoById(Integer id) {
        return prestamoRepo.findById(id);
    }

    public void updatePrestamo(Prestamos prestamo) {
        prestamoRepo.save(prestamo);
    }

    public void createPrestamo(Prestamos prestamo) {
        prestamoRepo.save(prestamo);
    }

    public List<Prestamos> findPrestamosByUsuario(Integer idUsuario) {
        return prestamoRepo.findByUsuario_CodiUsua(idUsuario);
    }

    public List<Prestamos> findPrestamosByEquipo(Integer idEquipo) {
        return prestamoRepo.findByEquipos_CodiEquipo(idEquipo);
    }

    public List<Prestamos> findPrestamosByEstado(short estado) {
        return prestamoRepo.findByEstado(estado);
    }

    // Método que faltaba
    public List<Prestamos> findAllPrestamos() {
        return prestamoRepo.findAll();
    }
}