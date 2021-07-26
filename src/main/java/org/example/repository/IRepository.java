package org.example.repository;

import org.example.domain.Entity;

import java.util.List;

public interface IRepository<T extends Entity> {
    /**
     * Add entity to the repository
     * @param entity the entity to add
     * @throws RepositoryException if the id already exists
     */
    void create(T entity) throws RepositoryException;

    /**
     * Gets a entity with a given id.
     * @param id the id
     * @return the entity with the given id, or null if none exists
     */
    T readOne(int id);

    /**
     * Returns all entity
     * @return all entity
     */
    List<T> read();

    /**
     * Updates a given entity by its id
     * @param entity the given entity
     * @throws RepositoryException if the entity id does not exist
     */
    void update(T entity) throws RepositoryException;

    /**
     * Deletes an entity with a given id
     * @param id the id of the entity to delete
     * @throws RepositoryException if there is no entity with the given id
     */
    void delete(int id) throws RepositoryException;
}
