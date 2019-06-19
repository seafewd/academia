/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.utilities.pdf;

import ch.bfh.ti.soed.academia.backend.models.Student;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * PDF Printer class that prints a Confirmation of Enrollment using iText plugin with an output stream.
 */
public class PdfPrinter {
    public String printPDF(Student student) {
        Document document = new Document();
        String fileName = "enrollmentConfirmation.pdf";
        String workingDirectory = System.getProperty("user.home") + "/Desktop";
        String absoluteFilePath = workingDirectory + File.separator + fileName;
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        //font styles
        Font headingFont = FontFactory.getFont(FontFactory.defaultEncoding, 15, Font.NORMAL, new CMYKColor(255, 255, 255, 255));
        Font boldWhiteFont = FontFactory.getFont(FontFactory.defaultEncoding, 10, Font.BOLD, new CMYKColor(0, 0, 0, 0));

        //Create writer and add elements to the document
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(absoluteFilePath));
            document.open();
            document.add(new Paragraph("Date: " + dtf.format(localDate))); //hehe
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Confirmation of enrollment", headingFont));
            document.add(new Paragraph(" "));

            //table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingAfter(0);
            table.setSpacingBefore(0);

            PdfPCell emptyRow = new PdfPCell(new Paragraph(" "));
            emptyRow.setBorder(Rectangle.NO_BORDER);
            table.addCell(emptyRow);
            table.addCell(emptyRow);

            PdfPCell pInfo = new PdfPCell(new Paragraph("Personal information", boldWhiteFont));
            pInfo.setBorder(Rectangle.BOTTOM);
            pInfo.setBackgroundColor(BaseColor.GRAY);
            table.addCell(pInfo);

            PdfPCell pInfoRight = new PdfPCell(new Paragraph(""));
            pInfoRight.setBorder(Rectangle.BOTTOM);
            pInfoRight.setBackgroundColor(BaseColor.GRAY);
            table.addCell(pInfoRight);
            table.completeRow();

            PdfPCell firstNameLabel = new PdfPCell(new Paragraph("First name"));
            firstNameLabel.setBorder(Rectangle.NO_BORDER);
            table.addCell(firstNameLabel);

            PdfPCell firstName = new PdfPCell(new Paragraph(student.getFirstName()));
            firstName.setBorder(Rectangle.NO_BORDER);
            table.addCell(firstName);
            table.completeRow();

            PdfPCell lastNameLabel = new PdfPCell(new Paragraph("Last name"));
            lastNameLabel.setBorder(Rectangle.NO_BORDER);
            table.addCell(lastNameLabel);

            PdfPCell lastName = new PdfPCell(new Paragraph(student.getLastName()));
            lastName.setBorder(Rectangle.NO_BORDER);
            table.addCell(lastName);
            table.completeRow();

            table.addCell(emptyRow);
            table.addCell(emptyRow);
            table.completeRow();

            PdfPCell academicData = new PdfPCell(new Paragraph("Academic data", boldWhiteFont));
            academicData.setBorder(Rectangle.BOTTOM);
            academicData.setBackgroundColor(BaseColor.GRAY);
            table.addCell(academicData);

            PdfPCell academicDataRight = new PdfPCell(new Paragraph(""));
            academicDataRight.setBorder(Rectangle.BOTTOM);
            academicDataRight.setBackgroundColor(BaseColor.GRAY);
            table.addCell(academicDataRight);
            table.completeRow();

            PdfPCell degreeProgrammeLabel = new PdfPCell(new Paragraph("Degree programme"));
            degreeProgrammeLabel.setBorder(Rectangle.NO_BORDER);
            table.addCell(degreeProgrammeLabel);

            PdfPCell degreeProgramme = new PdfPCell(new Paragraph(student.getDegreeProgramsAsSet().toString()));
            degreeProgramme.setBorder(Rectangle.NO_BORDER);
            table.addCell(degreeProgramme);
            table.completeRow();

            PdfPCell currentStatusLabel = new PdfPCell(new Paragraph("Current status"));
            currentStatusLabel.setBorder(Rectangle.NO_BORDER);
            table.addCell(currentStatusLabel);

            PdfPCell currentStatus = new PdfPCell(new Paragraph(student.getStatus().toString()));
            currentStatus.setBorder(Rectangle.NO_BORDER);
            table.addCell(currentStatus);
            table.completeRow();

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("This is a confirmation that you are enrolled in the best school in the world. Sorta. Have fun."));

            document.close();
            writer.close();
            System.out.println("Successfully generated PDF at " + absoluteFilePath);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return absoluteFilePath;
    }
}
