package com.p1.JPA.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p1.JPA.modelos.Cliente;
import com.p1.JPA.modelos.Mascota;
import com.p1.JPA.respositorios.MascotaRepositorio;
@Service
public class ServicioMascota {
	@Autowired
	MascotaRepositorio mascotarep;

	public ArrayList<Mascota> GetMascotas() {
		return (ArrayList<Mascota>) mascotarep.findAll();
	}

	public Mascota SaveMascota(Mascota mascota) {
		return mascotarep.save(mascota);
	}

	public Optional<Mascota> GetMascotaId(Integer Codigo) {
		return mascotarep.findById(Codigo);
	}

	public void DelMascota(Integer Codigo) {
		mascotarep.deleteById(Codigo);
	}
	
	public void SaveAllMascota(List<Mascota> mascota) {
		mascotarep.saveAll(mascota);
	}
	public List<Mascota> GetMascotaCliente(Cliente cliente) {
		return mascotarep.findByCliente(cliente);
	}
}
