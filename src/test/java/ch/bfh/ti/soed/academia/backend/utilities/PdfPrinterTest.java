/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.utilities;

import ch.bfh.ti.soed.academia.backend.controllers.ModuleRunsController;
import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.models.Module;
import ch.bfh.ti.soed.academia.backend.utilities.pdf.PdfPrinter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.*;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TestingClass - Tests PdfPrinter class
 * @see PdfPrinter
 */
public class PdfPrinterTest {

    @Inject
    private ModuleRunsController moduleRunsController;

    private static EJBContainer container;

    /**
     * Start method, executed when this class is called
     */
    @BeforeAll
    public static void start() {
        container = EJBContainer.createEJBContainer();
    }

    /**
     * End method, executed when all tests are done
     */
    @AfterAll
    public static void stop() {
        container.close();
    }

    /**
     * StartTest method, executed right before each test
     * @throws NamingException namingException
     */
    // See: http://tomee.apache.org/developer/testing/other/index.html
    @BeforeEach
    public void inject() throws NamingException {
        container.getContext().bind("inject", this);
    }

    /**
     * EndTest method, executed right after each test
     * @throws NamingException namingException
     */
    @AfterEach
    public void reset() throws NamingException {
        container.getContext().unbind("inject");
    }

    @Test
    public void testPrintPDF() throws InvalidKeySpecException, NoSuchAlgorithmException, FileNotFoundException, DocumentException {
        Student student = new Student("Steve", "Aoki", StudentStatus.Enrolled);
        ModuleRun moduleRun = new ModuleRun(new Module("Math", ModuleType.PE, DegreeProgramme.ComputerScience,
                "blabla", new Professor()), Semester.FS2019, new HashSet<>(), new HashSet<>());
        this.moduleRunsController.subscribe(student,moduleRun);

        PdfPrinter printer = new PdfPrinter();
        assertNotNull(printer.printPDF(student));

        Document document = new Document();
        String fileName = "enrollmentConfirmation.pdf";

        String workingDirectory = System.getProperty("user.home") + "/Desktop";
        if (workingDirectory.contains("null"))
            workingDirectory = null;
        assertNotNull(workingDirectory);
        System.out.println("Working directory: " + workingDirectory);

        String absoluteFilePath = workingDirectory + File.separator + fileName;
        if (absoluteFilePath.contains("null"))
            absoluteFilePath = null;
        assertNotNull(absoluteFilePath);
        System.out.println("Absolute file path: " + absoluteFilePath);

        FileOutputStream fileOutputStream = new FileOutputStream(absoluteFilePath);

        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        document.open();
        document.add(new Paragraph(student.getLastName()));

        assertNotNull(writer);

        File file = new File(absoluteFilePath);
        assertNotNull(file);
        System.out.println("Test file created.");
        file.deleteOnExit();
        System.out.println("Test file deleted.");

        document.close();
        writer.close();

    }
}
