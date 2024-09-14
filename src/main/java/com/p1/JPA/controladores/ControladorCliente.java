package com.p1.JPA.controladores;

import java.util.ArrayList;
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
import com.p1.JPA.servicios.ServicioCliente;

@RestController
@RequestMapping("/Cliente")
public class ControladorCliente {
    
    @Autowired
    ServicioCliente clientesS;    
    
    @GetMapping()
    public ArrayList<Cliente> ObtenerClientes(){
        return clientesS.GetClientes();
    }
    
    @PostMapping()
    public Cliente GuardarCliente(@RequestBody Cliente cliente) {
        return this.clientesS.SaveCliente(cliente);
    }

    @GetMapping(path = "/{dni}")
    public Optional<Cliente> ObtenerClientePorDni(@PathVariable("dni") String dni) {
        return this.clientesS.GetClienteId(dni);
    }
    
    @PutMapping(path = "/{dni}")
    public Cliente ActualizarClientePorId(@PathVariable("dni") String dni, @RequestBody Cliente cliente) {
    	return this.clientesS.SaveCliente(cliente);
    }

    @DeleteMapping(path = "/{dni}")
    public String EliminarClientePorDni(@PathVariable("dni") String dni) {
        boolean ok = false;
        try {
            clientesS.DelCliente(dni);
            ok = true;
        } catch (Exception e) {
            ok = false;
        }

        if (ok) {
            return "Cliente con DNI " + dni + " fue eliminado.";
        } else {
            return "No se pudo eliminar el cliente con DNI " + dni;
        }
    }
    
}
