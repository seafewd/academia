/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.backend.services;

import ch.bfh.ti.soed.academia.backend.models.Professor;
import ch.bfh.ti.soed.academia.backend.models.Student;
import ch.bfh.ti.soed.academia.backend.models.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The User service.
 */
public class UserService {

    @PersistenceContext
    private EntityManager em;

    /**
     * @return all available User objects.
     */
    public List<User> findAll() {
        TypedQuery<? extends User> q1 = this.em.createNamedQuery("Student.findAll", Student.class);
        TypedQuery<? extends User> q2 = this.em.createNamedQuery("Professor.findAll", Professor.class);
        List<User> users = (List<User>) q1.getResultList();
        users.addAll(q2.getResultList());
        return users;
    }

    /**
     * Find the User by tag.
     *
     * @param tag of the User that we are searching
     * @return user
     */
    public User findByTag(String tag){
        TypedQuery<? extends  User> q = this.em.createNamedQuery("User.findByPattern", User.class);
        TypedQuery<? extends  User> q1 = this.em.createNamedQuery("Student.findByTag", Student.class);
        TypedQuery<? extends  User> q2 = this.em.createNamedQuery("Professor.findByTag", Professor.class);
        q.setParameter(1, tag);
        q1.setParameter(1, tag);
        q2.setParameter(1, tag);
        User user;
        try {
            user = q.getSingleResult();
            return user;
        }catch (NoResultException ne) {
            try {
                user = q1.getSingleResult();
                return user;
            } catch (NoResultException ne1) {
                try {
                    user = q2.getSingleResult();
                    return user;
                } catch (NoResultException ne2) {
                    return null;
                }
            }
        }
    }

    /**
     * Persists or updates a student in the persistence store. Also assigns an
     * identifier for new user instances.
     *
     * @param user a new user or a user being updated
     * @return a managed copy of the given user object having an id
     */
    public User save(User user) {
        User ns = em.merge(user);
        return ns;
    }
}
