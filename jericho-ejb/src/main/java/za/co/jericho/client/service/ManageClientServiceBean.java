package za.co.jericho.client.service;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
import za.co.jericho.client.domain.Purchaser;
import za.co.jericho.client.domain.Seller;
import za.co.jericho.client.search.PurchaserSearchCriteria;
import za.co.jericho.client.search.SellerSearchCriteria;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.interceptors.AuditTrailInterceptor;
import za.co.jericho.interceptors.SecurityPermissionInterceptor;
import za.co.jericho.interceptors.UserActivityMonitorInterceptor;
import za.co.jericho.security.ServiceName;
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
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManageClientServiceBean extends AbstractServiceBean
implements ManageClientService{

    @SecurityPermission(serviceName = ServiceName.ADD_SELLER)
    @AuditTrail(serviceName = ServiceName.ADD_SELLER)
    @Override
    public Seller addSeller(Seller seller) {
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
        return seller;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_SELLER)
    @AuditTrail(serviceName = ServiceName.UPDATE_SELLER)
    @Override
    public Seller updateSeller(Seller seller) {
        /* Validations */
        /* State validation */
        seller.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(seller)) {
            getEntityManager().merge(seller);
        }
        return seller;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_SELLER_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_SELLER_DELETED)
    @Override
    public Seller markSellerDeleted(Seller seller) {
        throw new DeleteNotSupportedException("Deleting a seller is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_SELLERS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_SELLERS)
    @Override
    public Collection<Seller> searchSellers(SellerSearchCriteria sellerSearchCriteria) {
        Collection<Seller> sellers = new ArrayList<>();
        if (sellerSearchCriteria != null) {
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
        }
        else {
            throw new ServiceBeanException("Seller search criteria not provided");
        }
        return sellers;
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_SELLERS)
    @Override
    public Seller findSeller(Object id) {
        return getEntityManager().find(Seller.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_SELLERS)
    @Override
    public Collection<Seller> findAllSellers() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Seller.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SecurityPermission(serviceName = ServiceName.ADD_PURCHASER)
    @AuditTrail(serviceName = ServiceName.ADD_PURCHASER)
    @Override
    public Purchaser addPurchaser(Purchaser purchaser) {
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
        return purchaser;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_PURCHASER)
    @AuditTrail(serviceName = ServiceName.UPDATE_PURCHASER)
    @Override
    public Purchaser updatePurchaser(Purchaser purchaser) {
        /* Validations */
        /* State validation */
        purchaser.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(purchaser)) {
            getEntityManager().merge(purchaser);
        }
        return purchaser;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_PURCHASER_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_PURCHASER_DELETED)
    @Override
    public Purchaser markPurchaserDeleted(Purchaser purchaser) {
        throw new DeleteNotSupportedException("Deleting a purchaser is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_PURCHASERS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_PURCHASERS)
    @Override
    public Collection<Purchaser> searchPurchasers(PurchaserSearchCriteria purchaserSearchCriteria) {
        Collection<Purchaser> purchasers = new ArrayList<>();
        if (purchaserSearchCriteria != null) {
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
            
        }
        else {
            throw new ServiceBeanException("Purchaser search criteria not provided");
        }
        return purchasers;
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_PURCHASERS)
    @Override
    public Purchaser findPurchaser(Object id) {
        return getEntityManager().find(Purchaser.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_PURCHASERS)
    @Override
    public Collection<Purchaser> findAllPurchasers() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Purchaser.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

	

}