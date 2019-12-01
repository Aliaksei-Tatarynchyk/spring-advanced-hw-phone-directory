package com.epam.phone.directory.service.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.epam.phone.directory.model.db.PhoneNumber;
import com.epam.phone.directory.model.db.User;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);

    public ByteArrayInputStream generateUsersPDF(Collection<User> users) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            addUsersToPDFDocument(document, users);

            document.close();
        } catch (DocumentException e) {
            logger.error("Exception happened during generating Users PDF file: ", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public static void addUsersToPDFDocument(Document document, Collection<User> users) throws DocumentException {
        document.add(new Phrase("Users:"));
        document.add(takeUsersTable(users));
    }

    private static PdfPTable takeUsersTable(Collection<User> users) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setWidths(new int[] {1,3} );

        for (User user : users) {
            table.addCell(user.getFullName());
            table.addCell(takeUserPhoneNumbersString(user));
        }

        return table;
    }

    private static String takeUserPhoneNumbersString(User user) {
        StringBuilder sb = new StringBuilder();
        for (PhoneNumber phoneNumber : user.getPhoneNumbers()) {
            sb.append(phoneNumber.getValue());
            if (phoneNumber.getPhoneCompany() != null) {
                sb.append(" (").append(phoneNumber.getPhoneCompany().getName()).append(")");
            }
            sb.append("; ");
        }
        return sb.toString();
    }
}
