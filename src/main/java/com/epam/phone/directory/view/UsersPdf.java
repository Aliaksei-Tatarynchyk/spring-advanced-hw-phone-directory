package com.epam.phone.directory.view;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.epam.phone.directory.model.db.User;
import com.epam.phone.directory.service.pdf.PdfGenerator;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

public class UsersPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO: how to inject it as a bean?
        PdfGenerator.addUsersToPDFDocument(document, (Collection<User>) model.get("users"));
    }
}
