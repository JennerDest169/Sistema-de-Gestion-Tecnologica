package com.grupo04.GestionDeEquiposTEC.Repository;

import com.grupo04.GestionDeEquiposTEC.Entidad.Prestamos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamos, Integer> {
  List<Prestamos> findByUsuario_CodiUsua(Integer idUsuario);
  List<Prestamos> findByEquipos_CodiEquipo(Integer idEquipo);
  List<Prestamos> findByEstado(short estado);
}
