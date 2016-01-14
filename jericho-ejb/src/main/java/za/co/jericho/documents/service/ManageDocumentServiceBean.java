package za.co.jericho.documents.service;

import java.util.ArrayList;
import java.util.List;
import za.co.jericho.attorney.domain.Attorney;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.documents.domain.Document;
import za.co.jericho.documents.search.DocumentSearchCriteria;
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
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
public class ManageDocumentServiceBean extends AbstractServiceBean 
implements ManageDocumentService {

    /**
     * 
     * @param document
     * @return 
     */
    @Override
    public Document addDocument(Document document) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(document.getCreatedBy(), ServiceName.ADD_DOCUMENT.getValue());
        
        /* Validations */
        /* State validation */
        document.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(document)) {
            getEntityManager().persist(document);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.DOCUMENT, //EntityName entityName
            ServiceName.ADD_DOCUMENT.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            document.toString(), //String description
            document.getCreatedBy())); //User currentUser
        return document;
    }

    /**
     * 
     * @param document
     * @return 
     */
    @Override
    public Document updateDocument(Document document) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(document.getLastModifiedBy(), ServiceName.UPDATE_DOCUMENT.getValue());
        
        /* Validations */
        /* State validation */
        document.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(document)) {
            getEntityManager().merge(document);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.DOCUMENT, //EntityName entityName
            ServiceName.UPDATE_DOCUMENT.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            document.toString(), //String description
            document.getLastModifiedBy())); //User currentUser
        return document;
    }

    /**
     * 
     * @param document
     * @return 
     */
    @Override
    public Document markDocumentDeleted(Document document) {
        throw new DeleteNotSupportedException("Deleting a document is not supported");
    }

    /**
     * 
     * @param documentSearchCriteria
     * @return 
     */
    @Override
    public List<Document> searchDocuments(DocumentSearchCriteria documentSearchCriteria) {
        List<Document> documents = new ArrayList<>();
        if (documentSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(documentSearchCriteria.getServiceUser(), 
                ServiceName.SEARCH_DOCUMENTS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchAttorneysStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchAttorneysStringBuilder.append("SELECT d FROM Document d ");
            searchAttorneysStringBuilder.append("WHERE d.description like :description ");
            String description = stringConvertor.convertForDatabaseSearch
                (documentSearchCriteria.getDescription(), documentSearchCriteria.getSearchType());
            documents = getEntityManager().createQuery(searchAttorneysStringBuilder.toString())
                .setParameter("description", description)
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.DOCUMENT, //String entityName
            ServiceName.SEARCH_DOCUMENTS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                documentSearchCriteria.toString(), //String description
                documentSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Document search criteria not provided");
        }
        return documents;
    }

    @Override
    public Document findDocument(Object id) {
        return getEntityManager().find(Document.class, id);
    }

    @Override
    public List<Document> findAllDocuments() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Document.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}