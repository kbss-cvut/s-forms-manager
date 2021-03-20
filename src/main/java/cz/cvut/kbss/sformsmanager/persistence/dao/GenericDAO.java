package cz.cvut.kbss.sformsmanager.persistence.dao;

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
    List<T> findAll(String projectDescriptorName);

    /**
     * Finds all instances of the class that fit the where condition.
     *
     * @return All known instances that fit the condition
     */
    List<T> findAllWhere(String projectDescriptorName, String propertyName, Object value);

    /**
     * Finds entity instance with the specified identifier.
     *
     * @param id Identifier
     * @return {@code Optional} containing the matching entity instance or an empty {@code Optional }if no such instance
     * exists
     */
    Optional<T> find(URI id);

    /**
     * Finds first entity instance that fits the where condition.
     *
     * @return First instance that fits the condition
     */
    Optional<T> findFirstWhere(String projectDescriptorName, String propertyName, Object value);

    /**
     * Persists the specified entity.
     *
     * @param entity Entity to persist
     */
    void persist(String projectDescriptorName, T entity);

    /**
     * Persists the specified instances.
     *
     * @param entities Entities to persist
     */
    void persist(String projectDescriptorName, Collection<T> entities);

    /**
     * Updates the specified entity.
     *
     * @param entity Entity to update
     * @return The updated entity. Use it for further processing, as it could be a completely different instance
     */
    T update(String projectDescriptorName, T entity);

    /**
     * Removes the specified entity.
     *
     * @param entity Entity to remove
     */
    void remove(String projectDescriptorName, T entity);

    /**
     * Removes an entity with the specified id.
     *
     * @param id Entity identifier
     */
    void remove(URI id);

    /**
     * Checks whether an entity with the specified id exists (and has the type managed by this DAO).
     *
     * @param id Entity identifier
     * @return {@literal true} if entity exists, {@literal false} otherwise
     */
    boolean exists(URI id);

    /**
     * Retrieves number of entities of the specified type.
     *
     * @return
     */
    int count(String projectDescriptorName);

    /**
     * Retrieves number of entities of the specified type that fit the where condition.
     *
     * @return
     */
    int countWhere(String projectDescriptorName, String propertyName, Object value);
}
