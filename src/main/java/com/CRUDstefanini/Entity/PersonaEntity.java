package com.CRUDstefanini.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persona")
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPersona;

    @Column(name = "nombre", columnDefinition = "VARCHAR(255)")
    String nombre;

    @Column(name = "apellido", columnDefinition = "VARCHAR(255)")
    String apellido;

    @Column(name = "fechaNac",columnDefinition = "DATE")
    String fechaNac;

    @Column(name = "dni", columnDefinition = "INT")
    int dni;

}
