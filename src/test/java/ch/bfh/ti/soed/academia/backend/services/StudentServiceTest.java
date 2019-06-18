/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.models.StudentStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

 /**
 * TestingClass - Tests all Methods in class StudentService
 * @author Eric Dubuis &lt;eric.dubuis@bfh.ch&gt; and Frederik Heck
 * @see StudentService
 */
public class StudentServiceTest {

	private static EJBContainer container;

	@Inject
	private StudentService service;

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

	/**
	 *  Tests: save method
	 */
	@Test
	public void testSave() {
		Student s = new Student();
		s.setFirstName("Bill");
		s.setLastName("Clinton");
		s.setStatus(StudentStatus.Enrolled);
		Student ns = service.save(s);

		assertNotNull(ns);
		assertNotNull(ns.getId()); 		//getId method of Student tested

		assertEquals("Bill", ns.getFirstName());
		assertEquals("Clinton", ns.getLastName());
		assertEquals(StudentStatus.Enrolled, ns.getStatus());
	}

	/**
	 *  Tests: findAll method
	 */
	@Test
	public void testFindAll(){
		int count = service.findAll().size();
		service.save(new Student());
		service.save(new Student());
		assertEquals(count + 2, service.findAll().size()); //all new saved Students (so in this case 2) are found
	}

	/**
	 *  Tests: findAll(String) method
	 * @throws InvalidKeySpecException invalidKeySpecException
	 * @throws NoSuchAlgorithmException noSuchALgorithmException
	 */
	@Test

	public void testFindAllFiltered() throws InvalidKeySpecException, NoSuchAlgorithmException {
		int count = service.findAll("Zebra").size();
        service.save(new Student("Zebra","Test", null));
        assertEquals(count + 1, service.findAll("Zebra").size()); //all new saved Students with Zebra (so in this case 1) should be found
        assertEquals(service.findAll().size(), service.findAll("").size()); //Pattern is empty -> all items should be found
		assertEquals(service.findAll().size(), service.findAll(null).size()); //Pattern is null -> all items should be found
	}

	/**
	 *  Tests: testDelete method
	 *  requires that save and findAll is working
	 * @throws InvalidKeySpecException invalidKeySpecException
	 * @throws NoSuchAlgorithmException noSuchALgorithmException
	 */
	@Test
	public void testDelete() throws InvalidKeySpecException, NoSuchAlgorithmException {
		Student s = new Student("Sepp", "Hundeflöter", StudentStatus.Enrolled);
		Student s1 = service.save(s);

		int count = service.findAll().size();

		service.delete(s1);
		assertEquals(count-1, service.findAll().size());
	}
}