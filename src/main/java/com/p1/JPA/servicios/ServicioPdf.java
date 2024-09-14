package com.p1.JPA.servicios;

import com.itextpdf.html2pdf.HtmlConverter;
import com.p1.JPA.modelos.Mascota;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ServicioPdf {

    private String GenerarHtml(Mascota mascota) {
        StringBuilder html = new StringBuilder();

        html.append("<html>");
        html.append("<head><style>")
            .append("table { width: 100%; border-collapse: collapse; }")
            .append("table, th, td { border: 1px solid black; padding: 8px; text-align: left; }")
            .append(" .col { width: 50%; vertical-align: top; }") 
            .append("</style></head>");
        html.append("<body>");
        html.append("<h1>Información de la Mascota y del Cliente</h1>");
        html.append("<table>");
        html.append("<tr>");

        html.append("<td class='col'>");
        html.append("<h2>Mascota</h2>");
        html.append("<p><strong>Nombre Mascota:</strong> ").append(mascota.getNombre()).append("</p>");
        html.append("<p><strong>Raza:</strong> ").append(mascota.getRaza()).append("</p>");
        html.append("<p><strong>Edad:</strong> ").append(mascota.getEdad()).append(" años</p>");
        html.append("<p><strong>Peso:</strong> ").append(mascota.getPeso()).append(" kg</p>");
        html.append("</td>");

        html.append("<td class='col'>");
        html.append("<h2>Cliente</h2>");
        html.append("<p><strong>Dni:</strong> ").append(mascota.getCliente().getDni()).append("</p>");
        html.append("<p><strong>Nombre Cliente:</strong> ")
            .append(mascota.getCliente().getNombre()).append(" ")
            .append(mascota.getCliente().getApellido1()).append(" ")
            .append(mascota.getCliente().getApellido2()).append("</p>");
        html.append("<p><strong>Dirección:</strong> ").append(mascota.getCliente().getDireccion()).append("</p>");
        html.append("<p><strong>Teléfono:</strong> ").append(mascota.getCliente().getTelefono()).append("</p>");
        html.append("</td>");

        html.append("</tr>");
        html.append("</table>");

        html.append("<h2>Medicamentos</h2>");
        html.append("<table>");
        html.append("<tr><th>Nombre Medicamento</th><th>Dosis</th></tr>");

        mascota.getMedicamentos().forEach(med -> {
            html.append("<tr><td>").append(med.getNombre()).append("</td>")
                .append("<td>").append(med.getDosis()).append("</td></tr>");
        });

        html.append("</table>");
        html.append("</body>");
        html.append("</html>");
        

        return html.toString();
    }


    public ByteArrayInputStream GenerarPdf(Mascota mascota) {
        try {
           
            String html = GenerarHtml(mascota);
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(new ByteArrayInputStream(html.getBytes()), pdfOutputStream);

            return new ByteArrayInputStream(pdfOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
