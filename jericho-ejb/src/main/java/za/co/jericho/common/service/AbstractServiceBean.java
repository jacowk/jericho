package za.co.jericho.common.service;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import za.co.jericho.audit.service.ManageAuditActivityService;

//@Interceptors({AuditActivityInterceptor.class, SecurityPermissionInterceptor.class})
public abstract class AbstractServiceBean implements Serializable {
    
    @PersistenceContext(name = "jericho-pu")
    private EntityManager entityManager;
    @EJB
    protected ManageAuditActivityService manageAuditActivityService;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}