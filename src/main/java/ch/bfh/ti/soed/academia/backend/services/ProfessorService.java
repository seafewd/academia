/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.Professor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The gateway to the persistence store for Professor entities.
 */
@Stateless
public class ProfessorService {

        @PersistenceContext
        private EntityManager em;

        public ProfessorService() {
            // Intentionally left empty.
        }

        /**
         * @return all available professor objects.
         */
        @SuppressWarnings("unchecked")
        public List<Professor> findAll() {
            TypedQuery<? extends Professor> q = this.em.createNamedQuery("Professor.findAll", Professor.class);
            return (List<Professor>) q.getResultList();
        }

        /**
         * Finds all professors that match given filter.
         *
         * @param pattern pattern (having zero or more '%' or '_' wild cards) that
         *                returned objects should match or null/empty string if all
         *                objects should be returned.
         * @return list of Professor objects
         */
        @SuppressWarnings("unchecked")
        public List<Professor> findAll(String pattern) {
            if (pattern == null || "".equals(pattern)) {
                pattern = "%"; // '%' is the wild card for any pattern
            }
            TypedQuery<? extends Professor> q = this.em.createNamedQuery("Professor.findByPattern", Professor.class);
            q.setParameter(1, pattern);
            return (List<Professor>) q.getResultList();
        }

        /**
         * Deletes a professor from the persistence store.
         *
         * @param value the Professor to be deleted
         */
        public void delete(Professor value) {
            Professor c = em.find(Professor.class, value.getId());
            em.remove(c);
        }

        /**
         * Persists or updates a professor in the persistence store. Also assigns an
         * identifier for new Professor instances.
         *
         * @param professor a new professor or a professor being updated
         * @return a managed copy of the given Professor object having an id
         */
        public Professor save(Professor professor) {
            // assert entry != null ...
            Professor ns = em.merge(professor);
            return ns;
        }

}
