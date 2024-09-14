package com.p1.JPA.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p1.JPA.modelos.Cliente;
import com.p1.JPA.modelos.Mascota;
import com.p1.JPA.modelos.Medicamento;
import com.p1.JPA.servicios.ServicioMascota;
import com.p1.JPA.servicios.ServicioMedicamento;

@RestController
@RequestMapping("/Medicamento")
public class ControladorMedicamento {

    @Autowired
    ServicioMedicamento medicamentoS;

    @GetMapping()
    public ArrayList<Medicamento> ObtenerMedicamentos() {
        return medicamentoS.GetMedicamentos();
    }
    
    @PostMapping()
    public Medicamento GuardarMedicamento(@RequestBody Medicamento medicamento) {
        return medicamentoS.SaveMedicamento(medicamento);
    }
    
    @GetMapping(path = "/{Codigo}")
    public Optional<Medicamento> ObtenerMedicamentoPorId(@PathVariable("Codigo") Integer id) {
        return medicamentoS.GetMedicamentoId(id);
    }
    
    @PutMapping(path = "/{Codigo}")
    public Medicamento ActualizarMedicamentoPorId(@PathVariable("Codigo") Integer Codigo, @RequestBody Medicamento medicamento) {
    	return medicamentoS.SaveMedicamento(medicamento);
    }
    
    @DeleteMapping(path = "/{Codigo}")
    public String EliminarMascota(@PathVariable("Codigo") Integer Codigo) {
        boolean ok = false;
        try {
        	medicamentoS.DelMedicamento(Codigo);
            ok = true;
        } catch (Exception e) {
            ok = false;
        }
        return ok ? "Se eliminó la medicina con Codigo " + Codigo : "No se pudo eliminar la medicina con Codigo " + Codigo;
    }
    
}
