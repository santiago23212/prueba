package com.p1.JPA.controladores;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.p1.JPA.modelos.Cliente;
import com.p1.JPA.modelos.Mascota;
import com.p1.JPA.modelos.Medicamento;
import com.p1.JPA.respositorios.ClienteRepositorio;
import com.p1.JPA.respositorios.MascotaRepositorio;
import com.p1.JPA.respositorios.MedicamentoRepositorio;
import com.p1.JPA.servicios.ServicioCliente;
import com.p1.JPA.servicios.ServicioMascota;
import com.p1.JPA.servicios.ServicioMedicamento;
import com.p1.JPA.servicios.ServicioPdf;

@Controller
@RequestMapping("/Veterinaria")
public class ControladorVeterinaria {
	
	@Autowired
	ServicioCliente clientesS;
	@Autowired
	ServicioMascota mascotasS;
	@Autowired
	ServicioMedicamento medicamentosS;
	@Autowired
	ServicioPdf pdfS;


	@GetMapping("/verMascotas")
	public String VerMascotas(Model model) {
		comprobarclientes();
		model.addAttribute("clientes", clientesS.GetClientes());
		model.addAttribute("mascotas", mascotasS.GetMascotas());
		model.addAttribute("newmascota", new Mascota());

		return "mascotas";
	}

	private void comprobarclientes() {
		List<Mascota> mascotalist = mascotasS.GetMascotas();
		for (Mascota mascota : mascotalist) {
			mascota.setNombClient(mascota.getCliente().getNombre() + " " + mascota.getCliente().getApellido1() + " "
					+ mascota.getCliente().getApellido2());
		}
		mascotasS.SaveAllMascota(mascotalist);

	}

	@PostMapping("/guardarMascota")
	public String guardarMascota(@ModelAttribute Mascota mascota,
			@RequestParam(required = true, name = "Dni") String Dni) {
		Optional<Cliente> clienteop = clientesS.GetClienteId(Dni);
		Cliente cliente = clienteop.orElse(new Cliente());
		mascota.setCliente(cliente);
		mascota.setNombClient(cliente.nombre + " " + cliente.apellido1 + " " + cliente.apellido2);
		mascotasS.SaveMascota(mascota);
		return "redirect:/Veterinaria/verMascotas";
	}

	@PostMapping("/editarMascota")
	public String editarMascota(@ModelAttribute Mascota mascota) {
		Optional<Cliente> clienteop = clientesS.GetClienteId(mascota.getNombClient());
		Cliente cliente = clienteop.orElse(new Cliente());
		mascota.setCliente(cliente);		
		mascota.setNombClient(cliente.nombre + " " + cliente.apellido1 + " " + cliente.apellido2);
		mascotasS.SaveMascota(mascota);
		return "redirect:/Veterinaria/verMascotas";
	}

	@PostMapping("/eliminarMascota")
	public String eliminarMascota(@ModelAttribute Mascota mascota) {
		System.out.println("Mascota eliminada: " + mascota.getCodigo());
		mascotasS.DelMascota(Integer.valueOf(mascota.getCodigo()));
		return "redirect:/Veterinaria/verMascotas";
	}

	@GetMapping("/verMedicamentos/{id}")
	public String verMedicamentos(Model model, @PathVariable String id) {
		Optional<Mascota> mascotaop = mascotasS.GetMascotaId(Integer.valueOf(id));
		Mascota mascota = mascotaop.orElse(new Mascota());

		Set<Medicamento> medicamentosid = mascota.getMedicamentos();

		model.addAttribute("newmedicamento", new Medicamento());
		model.addAttribute("newmascota", new Mascota());
		model.addAttribute("mascota", mascota);
		model.addAttribute("medicamentos", medicamentosid);
		return "medicamentosid";
	}

	@GetMapping("/verMedicamentos")
	public String verMedicamentos(Model model) {

		model.addAttribute("medicamentos", medicamentosS.GetMedicamentos());

		return "medicamentos";
	}

	// Método para guardar un nuevo medicamento
	@PostMapping("/guardarMedicamento")
	public String guardarMedicamento(@ModelAttribute Medicamento medicamento,
			@RequestParam(required = true, name = "idMascota") String idMascota) {
		Optional<Mascota> mascotaop = mascotasS.GetMascotaId(Integer.valueOf(idMascota));

		Mascota mascota = mascotaop.orElse(new Mascota());

		medicamento.setMascota(mascota);

		medicamentosS.SaveMedicamento(medicamento);

		mascota.addMedicamento(medicamento);

		mascotasS.SaveMascota(mascota);
		return "redirect:/Veterinaria/verMedicamentos/" + idMascota;

	}

	// Método para editar un medicamento existente
	@PostMapping("/editarMedicamento")
	public String editarMedicamento(@ModelAttribute Medicamento medicamento) {
		Optional<Medicamento> medicamentoOp = medicamentosS.GetMedicamentoId(medicamento.getCodigo());
		Medicamento medicamentoExistente = medicamentoOp.get();
		medicamentoExistente.setNombre(medicamento.getNombre());
		medicamentoExistente.setDosis(medicamento.getDosis());
		medicamentosS.SaveMedicamento(medicamentoExistente);
		return "redirect:/Veterinaria/verMedicamentos/" + medicamentoExistente.getMascota().getCodigo();

	}

	// Método para eliminar un medicamento
	@PostMapping("/eliminarMedicamento")
	public String eliminarMedicamento(@RequestParam("Codigo") Integer id) {
		Optional<Medicamento> medicamentoOp = medicamentosS.GetMedicamentoId(id);

		Medicamento medicamento = medicamentoOp.get();
		medicamentosS.DelMedicamento(id);
		return "redirect:/Veterinaria/verMedicamentos/" + medicamento.getMascota().getCodigo();

	}

	@GetMapping("/verClientes")
	public String VerClientes(Model model) {
		model.addAttribute("clientes", clientesS.GetClientes());
		model.addAttribute("newcliente", new Cliente());
		return "clientes";
	}

	@PostMapping("/guardarCliente")
	public String guardarCliente(@ModelAttribute("newcliente") Cliente cliente) {
		clientesS.SaveCliente(cliente);
		return "redirect:/Veterinaria/verClientes";
	}

	@PostMapping("/editarCliente")
	public String editarCliente(@ModelAttribute("newcliente") Cliente cliente) {

		clientesS.SaveCliente(cliente);
		return "redirect:/Veterinaria/verClientes";
	}

	@PostMapping("/eliminarCliente")
	public String eliminarCliente(@RequestParam("Dni") String dni, RedirectAttributes redirectAttributes) {
		try {
			clientesS.DelCliente(dni);
			redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado exitosamente.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "No se puede eliminar un cliente con mascotas asociadas");
		}
		return "redirect:/Veterinaria/verClientes";
	}

	@GetMapping("/verMascotas/{id}")
	public String verMascotasid(Model model, @PathVariable String id) {
		Optional<Cliente> clienteop = clientesS.GetClienteId(id);
		Cliente cliente = clienteop.orElse(new Cliente());

		model.addAttribute("mascotas", mascotasS.GetMascotaCliente(cliente));
		model.addAttribute("cliente", cliente);
		return "mascotasid";
	}
	
	@GetMapping("/generarPdf/{id}")
    public ResponseEntity<byte[]> GenerarMascotaPdf(@PathVariable Integer id) {
        Mascota mascota = mascotasS.GetMascotaId(id).orElse(null);

        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayInputStream pdfStream = pdfS.GenerarPdf(mascota);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=mascota_" +mascota.getCliente().getDni()+ mascota.getNombre()+ ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.readAllBytes());
    }
}
