package za.co.jericho.audit.service;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import za.co.jericho.audit.domain.AuditActivity;
import za.co.jericho.audit.search.AuditActivitySearchCriteria;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

@Stateless
@Remote(ManageAuditActivityService.class)
public class ManageAuditActivityServiceBean extends AbstractServiceBean
implements ManageAuditActivityService {

   @Override
    public AuditActivity addAuditActivity(AuditActivity auditActivity) {
        Logger.getRootLogger().info("ManageAuditActivityServiceBean: addAuditActivity: " + 
            auditActivity.getDescription());
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(auditActivity)) {
            getEntityManager().persist(auditActivity);
        }
        return auditActivity;
    }

    @Override
    public Collection<AuditActivity> searchAuditActivities(AuditActivitySearchCriteria auditActivitySearchCriteria) {
        Logger.getRootLogger().info("ManageAuditActivityServiceBean: searchAuditActivities");
        Collection<AuditActivity> auditActivities = new ArrayList<>();
        if (auditActivitySearchCriteria != null) {
            if (auditActivitySearchCriteria.getActivityFromDate() == null) {
                throw new ServiceBeanException("Audit Activity from date not provided for search");
            }
            if (auditActivitySearchCriteria.getActivityToDate() == null) {
                throw new ServiceBeanException("Audit Activity to date not provided for search");
            }
            if (auditActivitySearchCriteria.getUser() == null) {
                throw new ServiceBeanException("Audit Activity User not provided for search");
            }
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT aa FROM AuditActivity aa ");
            searchUsersStringBuilder.append("WHERE aa.createDate BETWEEN :activityFromDate AND :activityEndDate ");
            searchUsersStringBuilder.append("AND aa.createdBy = :activityUser ");
            auditActivities = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("activityFromDate", auditActivitySearchCriteria.getActivityFromDate())
                .setParameter("activityEndDate", auditActivitySearchCriteria.getActivityToDate())
                .setParameter("activityUser", auditActivitySearchCriteria.getUser())
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Audit Activity search criteria not provided");
        }
        return auditActivities;
    }

}