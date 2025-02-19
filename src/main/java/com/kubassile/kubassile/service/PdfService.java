package com.kubassile.kubassile.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

@Service
public class PdfService {

    public Byte[] createPdf(String header, String text) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            PdfWriter pdfWriter = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            addBody(document, text);
            addHeader(document, header);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addHeader(Document document, String text) {
        Paragraph header = new Paragraph(text);
        header.setTextAlignment(TextAlignment.CENTER);
        header.setFontSize(20);
        header.setBold();

        document.add(header);
    }

    public void addBody(Document document, String text) {
        Paragraph body = new Paragraph(text);
        body.setTextAlignment(TextAlignment.CENTER);
        body.setFontSize(12);

        document.add(body);

    }

}
