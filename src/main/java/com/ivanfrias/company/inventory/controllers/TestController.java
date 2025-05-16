package com.ivanfrias.company.inventory.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        // Esto es simplemente sirve para testear que el microservicio funciona correctamente protegiendo la API
        return ResponseEntity.ok("Test ejecutado con éxito");
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() throws IOException, DocumentException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Informe de la empresa", titleFont);

        document.add(title);
        document.add(Chunk.NEWLINE);

        int i = 0;
        while(i < 5) {
            document.add(new Paragraph("Hola, este es un PDF generado desde Spring Boot."));
            i++;
        }
        document.close();

        byte[] pdfBytes = out.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("documento.pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
