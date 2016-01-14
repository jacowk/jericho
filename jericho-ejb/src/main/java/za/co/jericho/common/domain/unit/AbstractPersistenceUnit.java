package za.co.jericho.common.domain.unit;

import javax.persistence.EntityManager;

/**
 * This class represents an isolated unit of code that interacts with the entity 
 * manager.
 * 
 * @author Jaco Koekemoer
 * Date: 2016-01-07
 */
public abstract class AbstractPersistenceUnit implements Unit {
    
    private EntityManager entityManager;
    
    public AbstractPersistenceUnit() {
        
    }
    
    public AbstractPersistenceUnit(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
