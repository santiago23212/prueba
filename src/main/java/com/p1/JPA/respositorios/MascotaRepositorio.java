package com.p1.JPA.respositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.p1.JPA.modelos.Cliente;
import com.p1.JPA.modelos.Mascota;

public interface MascotaRepositorio extends JpaRepository<Mascota,Integer>{
	 List<Mascota> findByCliente(Cliente cliente);
}
