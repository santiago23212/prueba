package com.p1.JPA.modelos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="medicamento")
public class Medicamento {
	@Id
	@SequenceGenerator(name="medicamento_sequence",
			sequenceName="medicamento_sequence",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
			generator="medicamento_sequence")
	@Column(name="codigo_med",
	updatable=false)
	public Integer codigo;
	
	@Column(name="nombre_med",
			nullable=false)
	@NonNull
	public String nombre;
	
	@Column(name="dosis_med",
			nullable=false)
	@NonNull
	public String dosis; 
	
	@ManyToOne(fetch = FetchType.LAZY, 
			optional = false)
	@JoinColumn(name = "cod_mas", 
				nullable = false)
	@NonNull
	@JsonBackReference
	private Mascota mascota;
}
