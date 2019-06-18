/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.Enrollment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The gateway to the persistence store for Enrollment entities.
 */
@Stateless
public class EnrollmentService {
    @PersistenceContext
    private EntityManager em;

    public EnrollmentService() {
        // Intentionally left empty.
    }

    /**
     * @return all available enrollment objects.
     */
    @SuppressWarnings("unchecked")
    public List<Enrollment> findAll() {
        TypedQuery<? extends Enrollment> q = this.em.createNamedQuery("Enrollment.findAll", Enrollment.class);
        return (List<Enrollment>) q.getResultList();
    }

    /**
     * Finds all enrollments that match given filter.
     *
     * @param pattern pattern (having zero or more '%' or '_' wild cards) that
     *                returned objects should match or null/empty string if all
     *                objects should be returned.
     * @return list of Enrollment objects
     */
    @SuppressWarnings("unchecked")
    public List<Enrollment> findAll(String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "%"; // '%' is the wild card for any pattern
        }
        TypedQuery<? extends Enrollment> q = this.em.createNamedQuery("Enrollment.findByPattern", Enrollment.class);
        q.setParameter(1, pattern);
        return (List<Enrollment>) q.getResultList();
    }

    /**
     * Find Enrollments by ID. If ID is blank, find all Enrollments
     * @param id ID of Enrollment
     * @return List of Enrollments by ID if query is not empty, in that case return list of all Enrollments
     * ----
     * NOT FINISHED, NOT CURRENTLY USED
     * ----
     */
    @SuppressWarnings("unchecked")
    public List<Enrollment> findById(Long id) {
        if (id == null) {
            TypedQuery<? extends Enrollment> q = this.em.createNamedQuery("Enrollment.findAll", Enrollment.class);
            return (List<Enrollment>) q.getResultList();
        }
        TypedQuery<? extends Enrollment> q = this.em.createNamedQuery("Enrollment.findById", Enrollment.class);
        q.setParameter(1, id);
        return (List<Enrollment>) q.getResultList();
    }

    /**
     * Deletes a enrollment from the persistence store.
     *
     * @param value the Enrollment to be deleted
     */
    public void delete(Enrollment value) {
        Enrollment c = em.find(Enrollment.class, value.getId());
        em.remove(c);
    }

    /**
     * Persists or updates a enrollment in the persistence store. Also assigns an
     * identifier for new Enrollment instances.
     *
     * @param enrollment a new enrollment or a enrollment being updated
     * @return a managed copy of the given Enrollment object having an id
     */
    public Enrollment save(Enrollment enrollment) {
        // assert entry != null ...
        Enrollment ns = em.merge(enrollment);
        return ns;
    }
}
