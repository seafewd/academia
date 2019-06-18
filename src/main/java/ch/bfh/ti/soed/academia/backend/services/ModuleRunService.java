/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.ModuleRun;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The gateway to the persistence store for ModuleRun entities.
 */
@Stateless
public class ModuleRunService {
    @PersistenceContext
    private EntityManager em;

    public ModuleRunService() {
        // Intentionally left empty.
    }

    /**
     * @return all available moduleRun objects.
     */
    @SuppressWarnings("unchecked")
    public List<ModuleRun> findAll() {
        TypedQuery<? extends ModuleRun> q = this.em.createNamedQuery("ModuleRun.findAll", ModuleRun.class);
        return (List<ModuleRun>) q.getResultList();
    }

    /**
     * Finds all moduleRuns that match given filter.
     *
     * @param pattern pattern (having zero or more '%' or '_' wild cards) that
     *                returned objects should match or null/empty string if all
     *                objects should be returned.
     * @return list of ModuleRun objects
     */
    @SuppressWarnings("unchecked")
    public List<ModuleRun> findAll(String pattern) {
        if (pattern == null || "".equals(pattern)) {
            TypedQuery<? extends ModuleRun> q = this.em.createNamedQuery("ModuleRun.findAll", ModuleRun.class);
            return (List<ModuleRun>) q.getResultList();
            //pattern = "%"; // '%' is the wild card for any pattern
        }
        else {
            TypedQuery<? extends ModuleRun> q = this.em.createNamedQuery("ModuleRun.findByPattern", ModuleRun.class);
            q.setParameter(1, pattern);
            return (List<ModuleRun>) q.getResultList();
        }
    }

    /**
     * Deletes a moduleRun from the persistence store.
     *
     * @param value the ModuleRun to be deleted
     */
    public void delete(ModuleRun value) {
        ModuleRun c = em.find(ModuleRun.class, value.getId());
        em.remove(c);
    }

    /**
     * Persists or updates a moduleRun in the persistence store. Also assigns an
     * identifier for new ModuleRun instances.
     *
     * @param moduleRun a new moduleRun or a moduleRun being updated
     * @return a managed copy of the given ModuleRun object having an id
     */
    public ModuleRun save(ModuleRun moduleRun) {
        // assert entry != null ...
        ModuleRun ns = em.merge(moduleRun);
        return ns;
    }
}
