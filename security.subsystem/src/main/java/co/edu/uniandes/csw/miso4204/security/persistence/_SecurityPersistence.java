/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.miso4204.security.persistence;

import co.edu.uniandes.csw.miso4204.security.logic.dto.UserDTO;
import co.edu.uniandes.csw.miso4204.security.persistence.converter.UserConverter;
import co.edu.uniandes.csw.miso4204.security.persistence.entity.UserEntity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author estudiante
 */
public abstract class _SecurityPersistence {

    protected EntityManagerFactory emf;
    protected EntityManager entityManager;

    public UserDTO createUserSession(UserDTO user) {
        entityManager = emf.createEntityManager();
        UserEntity entity = UserConverter.persistenceDTO2Entity(user);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return UserConverter.entity2PersistenceDTO(entity);
    }

    @SuppressWarnings("unchecked")
    public List<UserDTO> getUserSessions() {
        entityManager = emf.createEntityManager();
        List<UserDTO> result;
        try {
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("select u from UserEntity u");
            result = UserConverter.entity2PersistenceDTOList(q.getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            result = new LinkedList<>();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return result;
    }

    public UserDTO getUserSession(Long id) {
        entityManager = emf.createEntityManager();
        UserDTO result;
        try {
            entityManager.getTransaction().begin();
            result = UserConverter.entity2PersistenceDTO(entityManager.find(UserEntity.class, id));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return result;
    }

    public UserDTO getUserSession(String userName) {
        entityManager = emf.createEntityManager();
        UserDTO result = null;
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.username = '" + userName + "'");
            List<UserEntity> records = query.getResultList();
            if (!records.isEmpty()) {
                result = UserConverter.entity2PersistenceDTO(records.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return result;
    }

    public void deleteUserSession(Long id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            UserEntity entity = entityManager.find(UserEntity.class, id);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public void updateUserSession(UserDTO detail) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            UserEntity entity = entityManager.merge(UserConverter.persistenceDTO2Entity(detail));
            UserConverter.entity2PersistenceDTO(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

}
