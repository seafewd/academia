/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.Student;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * The gateway to the persistence store for Student entities.
 */
@Stateless
public class StudentService {
	@PersistenceContext
	private EntityManager em;

	public StudentService() {
		// Intentionally left empty.
	}

	/**
	 * @return all available student objects.
	 */
	@SuppressWarnings("unchecked")
	public List<Student> findAll() {
		TypedQuery<? extends Student> q = this.em.createNamedQuery("Student.findAll", Student.class);
		return (List<Student>) q.getResultList();
	}

	/**
	 * Finds all students that match given filter.
	 *
	 * @param pattern pattern (having zero or more '%' or '_' wild cards) that
	 *                returned objects should match or null/empty string if all
	 *                objects should be returned.
	 * @return list of Student objects
	 */
	@SuppressWarnings("unchecked")
	public List<Student> findAll(String pattern) {
		if (pattern == null || "".equals(pattern)) {
			pattern = "%"; // '%' is the wild card for any pattern
		}
		TypedQuery<? extends Student> q = this.em.createNamedQuery("Student.findByPattern", Student.class);
		q.setParameter(1, pattern);
		return (List<Student>) q.getResultList();
	}

	/**
	 * Deletes a student from the persistence store.
	 *
	 * @param value the Student to be deleted
	 */
	public void delete(Student value) {
		Student c = em.find(Student.class, value.getId());
		em.remove(c);
	}

	/**
	 * Persists or updates a student in the persistence store. Also assigns an
	 * identifier for new Student instances.
	 *
	 * @param student a new student or a student being updated
	 * @return a managed copy of the given Student object having an id
	 */
	public Student save(Student student) {
		// assert entry != null ...
		Student ns = em.merge(student);
		return ns;
	}

	/**
	 * Find the Student by tag.
	 *
	 * @param tag of the Student that we are searching
	 * @return Student
	 */
	public Student getStudentByTag(String tag) {
		TypedQuery<? extends Student> q = this.em.createNamedQuery("Student.findByTag", Student.class);
		q.setParameter(1,tag);
		return q.getSingleResult();
	}
}
