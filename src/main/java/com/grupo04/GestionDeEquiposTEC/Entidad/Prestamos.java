package com.grupo04.GestionDeEquiposTEC.Entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prestamos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codi_prest")
    private Integer codiPrest;
    
    @ManyToOne
    @JoinColumn(name = "codi_usua", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "codi_equipo", nullable = false)
    private Equipos equipos;
    
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "fecha_debolucion")
    private LocalDateTime fechaDebolucion;

    @Column(name = "estado")
    private short estado;
}
