package com.p1.JPA.servicios;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p1.JPA.modelos.Medicamento;
import com.p1.JPA.respositorios.MascotaRepositorio;
import com.p1.JPA.respositorios.MedicamentoRepositorio;
@Service
public class ServicioMedicamento {
	@Autowired
	MedicamentoRepositorio medicamentorep;
	
	public ArrayList<Medicamento> GetMedicamentos(){
		return (ArrayList<Medicamento>) medicamentorep.findAll();		
	}
	public Medicamento SaveMedicamento(Medicamento medicamento) {
		return medicamentorep.save(medicamento);
	}
	public Optional<Medicamento> GetMedicamentoId(Integer Codigo) {
		return medicamentorep.findById(Codigo);
	}
	public void DelMedicamento(Integer Codigo) {
		medicamentorep.deleteById(Codigo);
	}
}
