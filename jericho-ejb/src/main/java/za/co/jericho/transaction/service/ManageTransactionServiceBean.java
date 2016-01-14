package za.co.jericho.transaction.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.transaction.domain.Transaction;
import za.co.jericho.transaction.domain.TransactionItem;
import za.co.jericho.transaction.search.TransactionSearchCriteria;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
@Stateless
@Remote(ManageTransactionService.class)
public class ManageTransactionServiceBean extends AbstractServiceBean 
implements ManageTransactionService {

    /**
     * 
     * @param transaction
     * @return 
     */
    @Override
    public Transaction addTransaction(Transaction transaction) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(transaction.getCreatedBy(), ServiceName
            .ADD_TRANSACTION.getValue());
        
        /* Validations */
        /* State validation */
        transaction.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(transaction)) {
            getEntityManager().persist(transaction);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.TRANSACTION, //EntityName entityName
            ServiceName.ADD_TRANSACTION.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            transaction.toString(), //String description
            transaction.getCreatedBy())); //User currentUser
        return transaction;
    }

    /**
     * 
     * @param transaction
     * @return 
     */
    @Override
    public Transaction updateTransaction(Transaction transaction) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(transaction.getLastModifiedBy(), ServiceName
            .UPDATE_TRANSACTION.getValue());
        
        /* Validations */
        /* State validation */
        transaction.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(transaction)) {
            getEntityManager().merge(transaction);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.TRANSACTION, //EntityName entityName
            ServiceName.UPDATE_TRANSACTION.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            transaction.toString(), //String description
            transaction.getLastModifiedBy())); //User currentUser
        return transaction;
    }

    /**
     * 
     * @param transaction
     * @return 
     */
    @Override
    public Transaction markTransactionDeleted(Transaction transaction) {
        throw new DeleteNotSupportedException("Deleting an transaction is not supported");
    }

    /**
     *
     * @param transactionSearchCriteria
     * @return
     */
    @Override
    public List searchTransactions(TransactionSearchCriteria 
        transactionSearchCriteria) {
        // TODO - implement ManageTransactionServiceBean.searchTransactions
        //Search transactions by account
        throw new UnsupportedOperationException("Searching transactions is not supported yet");
    }

    /**
     * 
     * @param transactionItem
     * @return 
     */
    @Override
    public TransactionItem addTransactionItem(TransactionItem transactionItem) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(transactionItem.getCreatedBy(), ServiceName
            .ADD_TRANSACTION_ITEM.getValue());
        
        /* Validations */
        /* State validation */
        transactionItem.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(transactionItem)) {
            getEntityManager().persist(transactionItem);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.TRANSACTION_ITEM, //EntityName entityName
            ServiceName.ADD_TRANSACTION_ITEM.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            transactionItem.toString(), //String description
            transactionItem.getCreatedBy())); //User currentUser
        return transactionItem;
    }

    /**
     * 
     * @param transactionItem
     * @return 
     */
    @Override
    public TransactionItem updateTransactionItem(TransactionItem transactionItem) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(transactionItem.getLastModifiedBy(), ServiceName
            .UPDATE_TRANSACTION_ITEM.getValue());
        
        /* Validations */
        /* State validation */
        transactionItem.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(transactionItem)) {
            getEntityManager().merge(transactionItem);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.TRANSACTION_ITEM, //EntityName entityName
            ServiceName.UPDATE_TRANSACTION_ITEM.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            transactionItem.toString(), //String description
            transactionItem.getLastModifiedBy())); //User currentUser
        return transactionItem;
    }

    /**
     * 
     * @param transactionItem
     * @return 
     */
    @Override
    public TransactionItem markTransactionItemDeleted(TransactionItem transactionItem) {
        throw new DeleteNotSupportedException("Deleting a transaction item is not supported");
    }

    @Override
    public List<TransactionItem> searchTransactionItems(TransactionSearchCriteria 
        transactionSearchCriteria) {
        //Search transaction items by transaction id
        throw new UnsupportedOperationException("Searching transaction items are not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}