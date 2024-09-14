package com.p1.JPA.controladores;

import com.p1.JPA.modelos.Mascota;
import com.p1.JPA.servicios.ServicioPdf;
import com.p1.JPA.servicios.ServicioMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class ControladorPdf {

    @Autowired
    private ServicioPdf serviciopdf;

    @Autowired
    private ServicioMascota servicioMascota;

    @GetMapping("/generarpdf/{id}")
    public ResponseEntity<byte[]> GenerarMascotaPdf(@PathVariable Integer id) {
        Mascota mascota = servicioMascota.GetMascotaId(id).orElse(null);

        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayInputStream pdfStream = serviciopdf.GenerarPdf(mascota);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=mascota_" +mascota.getCliente().getDni()+ mascota.getNombre()+ ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.readAllBytes());
    }
}
