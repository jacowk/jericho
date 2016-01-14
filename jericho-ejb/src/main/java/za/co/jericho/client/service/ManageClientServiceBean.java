package za.co.jericho.client.service;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.client.domain.Purchaser;
import za.co.jericho.client.domain.Seller;
import za.co.jericho.client.search.PurchaserSearchCriteria;
import za.co.jericho.client.search.SellerSearchCriteria;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

/**
 * @author Jaco Koekemoer
 * Date: 2015-11-07
 */
@Stateless
@Remote(ManageClientService.class)
public class ManageClientServiceBean extends AbstractServiceBean
implements ManageClientService{

    @Override
    public Seller addSeller(Seller seller) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(seller.getCreatedBy(), ServiceName.ADD_SELLER.getValue());
        
        /* Validations */
        /* State validation */
        seller.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(seller)) {
            getEntityManager().persist(seller);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.SELLER, //EntityName entityName
            ServiceName.ADD_SELLER.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            seller.toString(), //String description
            seller.getCreatedBy())); //User currentUser
        return seller;
    }

    @Override
    public Seller updateSeller(Seller seller) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(seller.getLastModifiedBy(), ServiceName
            .UPDATE_SELLER.getValue());
        
        /* Validations */
        /* State validation */
        seller.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(seller)) {
            getEntityManager().merge(seller);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.SELLER, //EntityName entityName
            ServiceName.UPDATE_SELLER.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            seller.toString(), //String description
            seller.getLastModifiedBy())); //User currentUser
        return seller;
    }

    @Override
    public Seller markSellerDeleted(Seller seller) {
        throw new DeleteNotSupportedException("Deleting a seller is not supported");
    }

    @Override
    public Collection<Seller> searchSellers(SellerSearchCriteria sellerSearchCriteria) {
        Collection<Seller> sellers = new ArrayList<>();
        if (sellerSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(sellerSearchCriteria.getServiceUser(), 
                ServiceName.SEARCH_SELLERS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchContactsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
//            searchContactsStringBuilder.append("SELECT s FROM Seller s ");
//            searchContactsStringBuilder.append("WHERE c.name like :name ");
//            searchContactsStringBuilder.append("AND c.workDescription like :workDescription ");
//            
//            String name = stringConvertor.convertForDatabaseSearch
//                (sellerSearchCriteria.getName(), sellerSearchCriteria.getSearchType());
//            String workDescription = stringConvertor.convertForDatabaseSearch
//                (sellerSearchCriteria.getWorkDescription(), sellerSearchCriteria.getSearchType());
//            
//            sellers = getEntityManager().createQuery(searchContactsStringBuilder.toString())
//                .setParameter("name", name)
//                .setParameter("workDescription", workDescription)
//                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.SELLER, //String entityName
            ServiceName.SEARCH_SELLERS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                sellerSearchCriteria.toString(), //String description
                sellerSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Seller search criteria not provided");
        }
        return sellers;
    }

    @Override
    public Seller findSeller(Object id) {
        return getEntityManager().find(Seller.class, id);
    }

    @Override
    public Collection<Seller> findAllSellers() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Seller.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Purchaser addPurchaser(Purchaser purchaser) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(purchaser.getCreatedBy(), ServiceName.ADD_PURCHASER.getValue());
        
        /* Validations */
        /* State validation */
        purchaser.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(purchaser)) {
            getEntityManager().persist(purchaser);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.PURCHASER, //EntityName entityName
            ServiceName.ADD_PURCHASER.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            purchaser.toString(), //String description
            purchaser.getCreatedBy())); //User currentUser
        return purchaser;
    }

    @Override
    public Purchaser updatePurchaser(Purchaser purchaser) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(purchaser.getLastModifiedBy(), ServiceName
            .UPDATE_PURCHASER.getValue());
        
        /* Validations */
        /* State validation */
        purchaser.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(purchaser)) {
            getEntityManager().merge(purchaser);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.PURCHASER, //EntityName entityName
            ServiceName.UPDATE_PURCHASER.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            purchaser.toString(), //String description
            purchaser.getLastModifiedBy())); //User currentUser
        return purchaser;
    }

    @Override
    public Purchaser markPurchaserDeleted(Purchaser purchaser) {
        throw new DeleteNotSupportedException("Deleting a purchaser is not supported");
    }

    @Override
    public Collection<Purchaser> searchPurchasers(PurchaserSearchCriteria purchaserSearchCriteria) {
        Collection<Purchaser> purchasers = new ArrayList<>();
        if (purchaserSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(purchaserSearchCriteria.getServiceUser(), 
                ServiceName.SEARCH_PURCHASERS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchContactsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
//            searchContactsStringBuilder.append("SELECT c FROM Purchaser c ");
//            searchContactsStringBuilder.append("WHERE c.name like :name ");
//            searchContactsStringBuilder.append("AND c.workDescription like :workDescription ");
//            
//            String name = stringConvertor.convertForDatabaseSearch
//                (purchaserSearchCriteria.getName(), purchaserSearchCriteria.getSearchType());
//            String workDescription = stringConvertor.convertForDatabaseSearch
//                (purchaserSearchCriteria.getWorkDescription(), purchaserSearchCriteria.getSearchType());
//            
//            purchasers = getEntityManager().createQuery(searchContactsStringBuilder.toString())
//                .setParameter("name", name)
//                .setParameter("workDescription", workDescription)
//                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.PURCHASER, //String entityName
            ServiceName.SEARCH_PURCHASERS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                purchaserSearchCriteria.toString(), //String description
                purchaserSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Purchaser search criteria not provided");
        }
        return purchasers;
    }

    @Override
    public Purchaser findPurchaser(Object id) {
        return getEntityManager().find(Purchaser.class, id);
    }

    @Override
    public Collection<Purchaser> findAllPurchasers() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Purchaser.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

	

}