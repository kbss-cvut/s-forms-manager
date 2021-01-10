package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Base interface for data access objects.
 *
 * @param <T> Type managed by this DAO
 */
public interface GenericDAO<T> {

    /**
     * Finds all instances of the class managed by this DAO.
     *
     * @return All known instances
     */
    List<T> findAll(EntityManager em);

    /**
     * Finds entity instance with the specified identifier.
     *
     * @param id Identifier
     * @return {@code Optional} containing the matching entity instance or an empty {@code Optional }if no such instance
     * exists
     */
    Optional<T> find(EntityManager em, URI id);

    /**
     * Gets a reference to an instance with the specified identifier.
     * <p>
     * Note that the reference is initially an empty object wth all attributes loaded lazily and the corresponding
     * persistence context has to be available for the loading. This method should be useful for removal and update
     * operations.
     *
     * @param id Identifier
     * @return {@code Optional} containing a reference to a matching instance or an empty {@code Optional }if no such
     * instance exists
     */
    Optional<T> getReference(EntityManager em, URI id);

    /**
     * Persists the specified entity.
     *
     * @param entity Entity to persist
     */
    void persist(EntityManager em, T entity);

    /**
     * Persists the specified instances.
     *
     * @param entities Entities to persist
     */
    void persist(EntityManager em, Collection<T> entities);

    /**
     * Updates the specified entity.
     *
     * @param entity Entity to update
     * @return The updated entity. Use it for further processing, as it could be a completely different instance
     */
    T update(EntityManager em, T entity);

    /**
     * Removes the specified entity.
     *
     * @param entity Entity to remove
     */
    void remove(EntityManager em, T entity);

    /**
     * Removes an entity with the specified id.
     *
     * @param id Entity identifier
     */
    void remove(EntityManager em, URI id);

    /**
     * Checks whether an entity with the specified id exists (and has the type managed by this DAO).
     *
     * @param id Entity identifier
     * @return {@literal true} if entity exists, {@literal false} otherwise
     */
    boolean exists(EntityManager em, URI id);

    int count(EntityManager em);
}
