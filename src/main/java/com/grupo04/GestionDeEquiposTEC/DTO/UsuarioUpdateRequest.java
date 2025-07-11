package com.grupo04.GestionDeEquiposTEC.DTO;

import lombok.Data;

@Data
public class UsuarioUpdateRequest {
    private Integer codiUsua;
    private String nombre;
    private String email;
    private Integer codiRol; // Solo necesitamos el ID del rol
}
