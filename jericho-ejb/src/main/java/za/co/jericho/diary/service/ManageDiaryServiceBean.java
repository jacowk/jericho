package za.co.jericho.diary.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.diary.domain.Diary;
import za.co.jericho.diary.domain.DiaryItem;
import za.co.jericho.diary.domain.FollowupItem;
import za.co.jericho.diary.search.DiarySearchCriteria;
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
 * Date: 2015-10-21
 */
@Stateless
@Remote(ManageDiaryService.class)
public class ManageDiaryServiceBean extends AbstractServiceBean
implements ManageDiaryService {

    /**
     * 
     * @param diary
     * @return 
     */
    @Override
    public Diary addDiary(Diary diary) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(diary.getCreatedBy(), ServiceName.ADD_DIARY.getValue());
        
        /* Validations */
        /* State validation */
        diary.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(diary)) {
            getEntityManager().persist(diary);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.DIARY, //EntityName entityName
            ServiceName.ADD_DIARY.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            diary.toString(), //String description
            diary.getCreatedBy())); //User currentUser
        return diary;
    }

    /**
     * 
     * @param diary
     * @return 
     */
    @Override
    public Diary updateDiary(Diary diary) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(diary.getLastModifiedBy(), ServiceName
            .UPDATE_DIARY.getValue());
        
        /* Validations */
        /* State validation */
        diary.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(diary)) {
            getEntityManager().merge(diary);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.DIARY, //EntityName entityName
            ServiceName.UPDATE_DIARY.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            diary.toString(), //String description
            diary.getLastModifiedBy())); //User currentUser
        return diary;
    }

    /**
     * 
     * @param diary
     * @param diaryItem
     */
    @Override
    public void diarise(Diary diary, DiaryItem diaryItem) {

    }
    
    /**
     * 
     * @param diaryItem
     * @return 
     */
    @Override
    public DiaryItem addDiaryItem(DiaryItem diaryItem) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(diaryItem.getCreatedBy(), ServiceName.ADD_DIARY_ITEM.getValue());
        
        /* Validations */
        /* State validation */
        diaryItem.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(diaryItem)) {
            getEntityManager().persist(diaryItem);
            /* At this stage the property object will not have an id assigned,
            until after the return, the transaction has committed. */
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.DIARY_ITEM, //EntityName entityName
            ServiceName.ADD_DIARY_ITEM.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            diaryItem.toString(), //String description
            diaryItem.getCreatedBy())); //User currentUser
        return diaryItem;
    }

    /**
     * 
     * @param diaryItem
     * @return 
     */
    @Override
    public DiaryItem updateDiaryItem(DiaryItem diaryItem) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(diaryItem.getLastModifiedBy(), ServiceName
            .UPDATE_DIARY_ITEM.getValue());
        
        /* Validations */
        /* State validation */
        diaryItem.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(diaryItem)) {
            getEntityManager().merge(diaryItem);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.DIARY_ITEM, //EntityName entityName
            ServiceName.UPDATE_DIARY_ITEM.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            diaryItem.toString(), //String description
            diaryItem.getLastModifiedBy())); //User currentUser
        return diaryItem;
    }

    /**
     * 
     * @param diaryItem
     * @param followupItem
     * @return 
     */
    @Override
    public FollowupItem followup(FollowupItem diaryItem, FollowupItem followupItem) {
        return null;
    }

    /**
     * 
     * @param followupItem
     * @return 
     */
    @Override
    public FollowupItem updateFollowupItem(FollowupItem followupItem) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(followupItem.getLastModifiedBy(), ServiceName
            .UPDATE_FOLLOWUP_ITEM.getValue());
        
        /* Validations */
        /* State validation */
        followupItem.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(followupItem)) {
            getEntityManager().merge(followupItem);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.FOLLOWUP_ITEM, //EntityName entityName
            ServiceName.UPDATE_FOLLOWUP_ITEM.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            followupItem.toString(), //String description
            followupItem.getLastModifiedBy())); //User currentUser
        return followupItem;
    }

    /**
     * 
     * @param diarySearchCriteria
     * @return 
     */
    @Override
    public List<DiaryItem> searchDiaryItems(DiarySearchCriteria diarySearchCriteria) {
        List<DiaryItem> diaryItems = new ArrayList<>();
        if (diarySearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(diarySearchCriteria.getServiceUser(), 
                ServiceName.SEARCH_DIARY_ITEMS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchContactsStringBuilder = new StringBuilder();
            StringValidator stringValidator = new StringDataValidator();
            
            //TODO: Finish this query
            searchContactsStringBuilder.append("SELECT c FROM Contractor c ");
            searchContactsStringBuilder.append("WHERE c.name like :name ");
            searchContactsStringBuilder.append("AND c.workDescription like :workDescription ");
            searchContactsStringBuilder.append("AND c.contacts.firstname like :firstname ");
            searchContactsStringBuilder.append("AND c.contacts.surname like :surname ");
            searchContactsStringBuilder.append("AND c.contacts.workEmail like :workEmail ");
            searchContactsStringBuilder.append("AND c.contacts.personalEmail like :personalEmail ");
//            String name = stringConvertor.convertForDatabaseSearch
//                (diarySearchCriteria.getName(), diarySearchCriteria.getSearchType());
//            diaryItems = getEntityManager().createQuery(searchContactsStringBuilder.toString())
//                .setParameter("name", name)
//                .setParameter("workDescription", workDescription)
//                .setParameter("firstname", firstname)
//                .setParameter("surname", surname)
//                .setParameter("workEmail", workEmail)
//                .setParameter("personalEmail", personalEmail)
//                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.DIARY_ITEM, //String entityName
            ServiceName.SEARCH_DIARY_ITEMS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                diarySearchCriteria.toString(), //String description
                diarySearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Diary item search criteria not provided");
        }
        return diaryItems;
    }

    /**
     * 
     * @param diary
     * @return 
     */
    @Override
    public Diary selfAllocateDiary(Diary diary) {
        return updateDiary(diary);
    }

}