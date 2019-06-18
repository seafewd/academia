/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.Module;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The gateway to the persistence store for Module entities.
 */
@Stateless
public class ModuleService {

    @PersistenceContext
    private EntityManager em;

    public ModuleService() {
        // Intentionally left empty.
    }

    /**
     * @return all available module objects.
     */
    @SuppressWarnings("unchecked")
    public List<Module> findAll() {
        TypedQuery<? extends Module> q = this.em.createNamedQuery("Module.findAll", Module.class);
        return (List<Module>) q.getResultList();
    }

    /**
     * Finds all modules that match given filter.
     *
     * @param pattern pattern (having zero or more '%' or '_' wild cards) that
     *                returned objects should match or null/empty string if all
     *                objects should be returned.
     * @return list of Module objects
     */
    @SuppressWarnings("unchecked")
    public List<Module> findAll(String pattern) {
        //TypedQuery<? extends Module> q = this.em.createNamedQuery("Module.findAll", Module.class);
        //return (List<Module>) q.getResultList();

        if (pattern == null || "".equals(pattern)) {
            TypedQuery<? extends Module> q = this.em.createNamedQuery("Module.findAll", Module.class);
            return (List<Module>) q.getResultList();
            //pattern = "%"; // '%' is the wild card for any pattern
        }

        else {
            TypedQuery<? extends Module> q = this.em.createNamedQuery("Module.findByPattern", Module.class);
            q.setParameter(1, pattern);
            return (List<Module>) q.getResultList();
        }
    }

    /**
     * Deletes a module from the persistence store.
     *
     * @param value the Module to be deleted
     */
    public void delete(Module value) {
        Module c = em.find(Module.class, value.getId());
        em.remove(c);
    }

    /**
     * Persists or updates a module in the persistence store. Also assigns an
     * identifier for new Module instances.
     *
     * @param module a new module or a module being updated
     * @return a managed copy of the given Module object having an id
     */
    public Module save(Module module) {
        // assert entry != null ...
        Module ns = em.merge(module);
        return ns;
    }
}
