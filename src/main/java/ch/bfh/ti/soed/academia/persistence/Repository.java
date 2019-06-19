/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.persistence;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.bfh.ti.soed.academia.backend.models.*;
import ch.bfh.ti.soed.academia.backend.models.Module;

@Stateless
public class Repository {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * empty constructor
	 */
	public Repository() {
		//left empty
	}

	/**
	 * Checks if repository is empty using lists of objects and then checking if all are empty
	 * @return Boolean
	 */
	public Boolean isEmpty(){
		List<Student> students = this.findAll("Student");
		List<Professor> professors = this.findAll("Professor");
		List<ModuleRun> moduleRuns = this.findAll("ModuleRun");
		//List<DegreeProgramme> degreeProgramme = this.findAll("DegreeProgramme");
		List<Module> modules = this.findAll("Module");
		List<Enrollment> enrollments = this.findAll("Enrollment");

		//true if all lists are empty, else false
		if (
				students.isEmpty() &&
				professors.isEmpty() &&
				moduleRuns.isEmpty() &&
				//degreeProgramme.isEmpty() &&
				modules.isEmpty() &&
				enrollments.isEmpty()
				) return true;
		else
			return false;
	}

	/**
	 * Find all objects of type 'type'
	 *
	 * @param <T> Class being searched for
	 * @param type String to filter results
	 * @return List of all type
	 */
	public <T> List<T> findAll(String type) {
		// TODO Use typed queries
		return this.entityManager.createQuery("SELECT t from " +  type + " t").getResultList();
	}


	/**
	 * Creates a JPA persistent Enrollment object
	 * @param moduleRun     ModuleRun object associated with this enrollment
	 * @param student       Student object associated with this enrollment
	 * @param evaluation    Enum associated with this enrollment

	 * @return Enrollment object
	 */
	public Enrollment createEnrollment(ModuleRun moduleRun, Student student, Evaluation evaluation) {
		Enrollment enrollment = new Enrollment(moduleRun, student, evaluation);
		return enrollment;
	}

	/**
	 * Creates a JPA persistent Professor object
	 * @param firstName     First name of professor
	 * @param lastName      Last name of professor
	 * @throws InvalidKeySpecException invalidKeySpecException
	 * @throws NoSuchAlgorithmException noSuchALgorithmException
	 * @return Professor object
	 */

	public Professor createProfessor(String firstName, String lastName) throws InvalidKeySpecException, NoSuchAlgorithmException {
		Professor professor = new Professor(firstName, lastName);
		return professor;
	}

	/**
	 * Creates a JPA persistent Student object
	 * @param firstName First name of student
	 * @param lastName  Last name of student
	 * @param status    Current status of student (Enrolled, Exmatriculated etc.)
	 * @throws InvalidKeySpecException invalidKeySpecException
	 * @throws NoSuchAlgorithmException noSuchALgorithmException
	 * @return Student object
	 */
	public Student createStudent(String firstName, String lastName, StudentStatus status) throws InvalidKeySpecException, NoSuchAlgorithmException {
		Student student = new Student(firstName, lastName, status);
		return student;
	}

	/**
	 * Creates a JPA persistent Module object
	 * @param name          Name of module
	 * @param moduleType    Type of module
	 * @param courseOfStudy Type of study (Computer Science, Medical Computer Science, ...)
	 * @param description   Module description
	 * @param moduleManager Professor responsible for module

	 * @return module       Module object
	 */
	public Module createModule(String name, ModuleType moduleType, DegreeProgramme courseOfStudy, String description, Professor moduleManager) {
		Module module = new Module(name, moduleType, courseOfStudy, description, moduleManager);
		return module;

	}

	/**
	 * Creates a JPA persistent ModuleRun object
	 * @param module        Module object associated with this ModuleRun
	 * @param semester          Semester associated with this ModuleRun

	 * @return moduleRun    ModuleRun object
	 */
	public ModuleRun createModuleRun(Module module, Semester semester) {
		ModuleRun moduleRun = new ModuleRun(module, semester);
		return moduleRun;
	}

	public User createAdmin(String tag, String password, Role role) throws InvalidKeySpecException, NoSuchAlgorithmException {
		User user = new User(tag, password, role);
		return user;
	}
}
