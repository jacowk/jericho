package za.co.jericho.notes.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.*;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.notes.domain.*;
import za.co.jericho.notes.search.NoteSearchCriteria;
import za.co.jericho.propertyflip.domain.*;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;
import za.co.jericho.util.validation.StringDataValidator;
import za.co.jericho.util.validation.StringValidator;

@Stateless
@Remote(ManageNoteService.class)
public class ManageNoteServiceBean extends AbstractServiceBean
implements ManageNoteService{

    /**
     * 
     * @param propertyFlip
     * @param note
     * @return Note
     */
    @Override
    public Note addNote(PropertyFlip propertyFlip, Note note) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(note.getCreatedBy(), ServiceName.ADD_NOTE.getValue());
        
        /* Validations */
        /* State validation */
        note.validate();
        propertyFlip.getNotes().add(note);
        note.setPropertyFlip(propertyFlip);
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(note)) {
            getEntityManager().persist(note);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.NOTE, //EntityName entityName
            ServiceName.ADD_NOTE.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            note.toString(), //String description
            note.getCreatedBy())); //User currentUser
        return note;
    }

    /**
     * 
     * @param note
     * @return Note
     */
    @Override
    public Note updateNote(Note note) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(note.getLastModifiedBy(), ServiceName
            .UPDATE_NOTE.getValue());
        
        /* Validations */
        /* State validation */
        note.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(note)) {
            getEntityManager().merge(note);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.NOTE, //EntityName entityName
            ServiceName.UPDATE_NOTE.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            note.toString(), //String description
            note.getLastModifiedBy())); //User currentUser
        return note;
    }

    /**
     * 
     * @param note
     * @return Note
     */
    @Override
    public Note markNoteDeleted(Note note) {
        throw new DeleteNotSupportedException("Deleting a note is not supported");
    }

    @Override
    public List<Note> searchNotes(NoteSearchCriteria noteSearchCriteria) {
        List<Note> notes = new ArrayList<>();
        if (noteSearchCriteria != null && noteSearchCriteria.getPropertyFlipSearchCriteria() != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(noteSearchCriteria.getServiceUser(), 
                ServiceName.SEARCH_ATTORNEYS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchAttorneysStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            searchAttorneysStringBuilder.append("SELECT n FROM Note n ");
            searchAttorneysStringBuilder.append("WHERE n.description like :description ");
            searchAttorneysStringBuilder.append("AND n.propertyFlip.id = :propertyFlipId;");
            String description = stringConvertor.convertForDatabaseSearch
                (noteSearchCriteria.getDescription(), noteSearchCriteria.getSearchType());
            notes = getEntityManager().createQuery(searchAttorneysStringBuilder.toString())
                .setParameter("description", description)
                .setParameter("propertyFlipId", noteSearchCriteria.getPropertyFlipSearchCriteria().getId())
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.NOTE, //String entityName
            ServiceName.SEARCH_NOTES.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                noteSearchCriteria.toString(), //String description
                noteSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Note search criteria not provided");
        }
        return notes;
    }

}