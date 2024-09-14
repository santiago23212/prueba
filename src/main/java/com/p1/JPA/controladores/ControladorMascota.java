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
import com.p1.JPA.servicios.ServicioMascota;

@RestController
@RequestMapping("/Mascota")
public class ControladorMascota {

    @Autowired
    ServicioMascota mascotaS;

    @GetMapping()
    public ArrayList<Mascota> ObtenerMascotas() {
        return mascotaS.GetMascotas();
    }
    
    @PostMapping()
    public Mascota GuardarMascota(@RequestBody Mascota mascota) {
        return mascotaS.SaveMascota(mascota);
    }
    
    @GetMapping(path = "/{Codigo}")
    public Optional<Mascota> ObtenerMascotaPorId(@PathVariable("Codigo") Integer id) {
        return mascotaS.GetMascotaId(id);
    }
    
    @PutMapping(path = "/{Codigo}")
    public Mascota ActualizarMascotaPorId(@PathVariable("Codigo") Integer Codigo, @RequestBody Mascota mascota) {
    	return mascotaS.SaveMascota(mascota);
    }
    
    @DeleteMapping(path = "/{Codigo}")
    public String EliminarMascota(@PathVariable("Codigo") Integer id) {
        boolean ok = false;
        try {
            mascotaS.DelMascota(id);
            ok = true;
        } catch (Exception e) {
            ok = false;
        }
        return ok ? "Se eliminó la mascota con id " + id : "No se pudo eliminar la mascota con id " + id;
    }
    
    @PostMapping("/guardarVarias")
    public List<Mascota> GuardarVariasMascotas(@RequestBody List<Mascota> mascotas) {
        mascotaS.SaveAllMascota(mascotas);
        return mascotas;
    }
    
}
