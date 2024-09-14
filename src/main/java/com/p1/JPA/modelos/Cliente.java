package com.p1.JPA.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cliente")
public class Cliente {
	@Id
	@NonNull
	@Column(unique= true, name="dni_cli",
	updatable=false)
	public String dni;
	
	@Column(name="nombre",
			nullable=false)
	@NonNull
	public String nombre; 
	@Column(name="apellido1",
			nullable=false)
	@NonNull
	public String apellido1;
	
	@Column(name="apellido2",
			nullable=false)
	@NonNull
	public String apellido2; 
	
	@Column(name="direccion",
			nullable=false)
	@NonNull
	public String direccion; 
	
	@Column(name="telefono",
			nullable=false)
	@NonNull
	public Integer telefono;

}
