/**
 * Copyright (C) 2019 Czech Technical University in Prague
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.sformsmanager.model.Student;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class StudentDao {

    // Notice that we are using Autowired instead of PersistenceContext, which is tightly coupled with traditional JPA
    private final EntityManager em;

    @Autowired
    public StudentDao(EntityManager em) {
        this.em = em;
    }

    public List<Student> findAll() {
        return em.createNamedQuery("Student.findAll", Student.class).getResultList();
    }

    public Student findByKey(String key) {
        try {
            return em.createNamedQuery("Student.findByKey", Student.class).setParameter("key", key, "en")
                    .getSingleResult();
        } catch (NoResultException e) {
            log.warn("Student with key {} not found.", key);
            return null;
        }
    }

    public void persist(Student student) {
        assert student != null;
        assert student.getUri() != null;

        em.persist(student);

        log.debug("Student {} persisted.", student);
    }

    public void delete(Student student) {
        assert student != null;

        final Student toRemove = em.merge(student);
        em.remove(toRemove);

        log.debug("Student {} deleted.", student);
    }
}
