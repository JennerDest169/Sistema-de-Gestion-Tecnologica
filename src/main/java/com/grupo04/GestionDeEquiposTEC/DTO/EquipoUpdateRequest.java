/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo04.GestionDeEquiposTEC.DTO;

import lombok.Data;

@Data
public class EquipoUpdateRequest {
    private Integer codiEquipo;
    private String nombre;
    private String descripcion;
    private String serie;
}
