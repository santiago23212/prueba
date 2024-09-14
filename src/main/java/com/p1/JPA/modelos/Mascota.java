package com.p1.JPA.modelos;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="mascota")
public class Mascota {
	
	@Id
	@SequenceGenerator(name="mascota_sequence",
	sequenceName="mascota_sequence",
	allocationSize=2)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
	generator="mascota_sequence")
	@Column(name="cod_mas",
	updatable=false)
	private Integer codigo;
	
	@Column(name="nombre_mas",
			nullable=false)
	@NonNull
	public String nombre;
	@Column(name="raza_mas",
			nullable=false)
	@NonNull
	public String raza;
	@Column(name="edad_mas",
			nullable=false)
	@NonNull
	public Integer edad;
	@Column(name="peso_mas",
			nullable=false)
	@NonNull
	public Float peso;
	
	@OneToMany(fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL, 
			mappedBy = "mascota")
	@JsonManagedReference
	public Set<Medicamento> medicamentos = new HashSet<>();
	
	
	@ManyToOne
	@JoinColumn(name="dni_cli")
	@NonNull
	public Cliente cliente;
	@Column(name="nomb_cliente_mas",
			nullable=false)
	@NonNull
	public String nombClient;
	
	public void addMedicamento(Medicamento medicamento) {
		this.medicamentos.add(medicamento);
	}
	public String generarXML(Mascota mascota) {
	    StringBuilder xml = new StringBuilder();
	    xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    xml.append("<mascota>");
	    xml.append("<nombre>").append(mascota.getNombre()).append("</nombre>");
	    xml.append("<raza>").append(mascota.getRaza()).append("</raza>");
	    xml.append("<edad>").append(mascota.getEdad()).append("</edad>");
	    xml.append("<peso>").append(mascota.getPeso()).append("</peso>");
	    
	    Cliente cliente = mascota.getCliente();
	    xml.append("<cliente>");
	    xml.append("<dni>").append(cliente.getDni()).append("</dni>");
	    xml.append("<nombre>").append(cliente.getNombre()).append("</nombre>");
	    xml.append("<apellido1>").append(cliente.getApellido1()).append("</apellido1>");
	    xml.append("<apellido2>").append(cliente.getApellido2()).append("</apellido2>");
	    xml.append("<direccion>").append(cliente.getDireccion()).append("</direccion>");
	    xml.append("<telefono>").append(cliente.getTelefono()).append("</telefono>");
	    xml.append("</cliente>");

	    xml.append("<medicamentos>");
	    for (Medicamento medicamento : mascota.getMedicamentos()) {
	        xml.append("<medicamento>");
	        xml.append("<nombre>").append(medicamento.getNombre()).append("</nombre>");
	        xml.append("<dosis>").append(medicamento.getDosis()).append("</dosis>");
	        xml.append("</medicamento>");
	    }
	    xml.append("</medicamentos>");
	    xml.append("</mascota>");

	    return xml.toString();
	}


}
