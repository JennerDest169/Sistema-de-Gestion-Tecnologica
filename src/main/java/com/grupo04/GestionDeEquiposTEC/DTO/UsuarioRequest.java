/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo04.GestionDeEquiposTEC.DTO;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String nombre;
    private String email;
    private String password;
    private Integer estado;
    private Integer codiRol; // Solo necesitamos el ID del rol
}
