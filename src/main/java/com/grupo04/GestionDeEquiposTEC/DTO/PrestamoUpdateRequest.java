package com.grupo04.GestionDeEquiposTEC.DTO;

import lombok.Data;

@Data
public class PrestamoUpdateRequest {
    private Integer idPrestamo; // Coincide con codi_prest
    private short nuevoEstado;  // Coincide con estado
}
