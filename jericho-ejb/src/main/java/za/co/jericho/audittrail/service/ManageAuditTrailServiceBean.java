package za.co.jericho.audittrail.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import za.co.jericho.audittrail.search.AuditTrailSearchCriteria;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.common.service.AbstractServiceBean;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.domain.Permission;
import za.co.jericho.security.domain.User;
import za.co.jericho.security.search.PermissionSearchCriteria;
import za.co.jericho.security.service.ManageSecurityPermissionService;
import za.co.jericho.security.service.ManageSecurityUserService;
import za.co.jericho.useractivity.domain.UserActivity;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-11-28
 */
@Stateless
@Remote(ManageAuditTrailService.class)
public class ManageAuditTrailServiceBean extends AbstractServiceBean
implements ManageAuditTrailService {
    
    @Resource
    private SessionContext sessionContext;
    @EJB
    private ManageSecurityUserService manageSecurityUserService;
    @EJB
    private ManageSecurityPermissionService manageSecurityPermissionService;

    @Override
    public AbstractAuditTrailEntity addAuditTrail(AbstractAuditTrailEntity auditTrail) {
        Logger.getRootLogger().info("ManageAuditTrailServiceBean: addAuditTrail");
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateAuditTrailEntityBeforeCreate(auditTrail)) {
            auditTrail.validate();
            getEntityManager().persist(auditTrail);
        }
        return auditTrail;
    }

    @Override
    public Collection<AbstractAuditTrailEntity> searchAuditTrails
        (AuditTrailSearchCriteria auditTrailSearchCriteria) {
        Logger.getRootLogger().info("ManageAuditTrailServiceBean: searchAuditTrails");
        Collection<AbstractAuditTrailEntity> auditTrails = new ArrayList<>();
        if (auditTrailSearchCriteria != null) {
            auditTrailSearchCriteria.validate();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT at FROM AuditTrail at ");
            searchUsersStringBuilder.append("WHERE at.createDate BETWEEN :auditTrailFromDate AND :auditTrailToDate ");
            searchUsersStringBuilder.append("AND at.createdBy = :auditTrailUser ");
            auditTrails = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("auditTrailFromDate", auditTrailSearchCriteria.getAuditTrailFromDate())
                .setParameter("auditTrailToDate", auditTrailSearchCriteria.getAuditTrailToDate())
                .setParameter("auditTrailUser", auditTrailSearchCriteria.getUser())
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Audit Trail search criteria not provided");
        }
        return auditTrails;
    }

    @Override
    public void createAuditTrail(ServiceName serviceName, Object entity) {
        ServiceFactory serviceFactory = AuditTrailServiceFactory.getInstance();
        AuditTrailService auditTrailService = serviceFactory
            .createAuditTrailService(serviceName);
        AbstractAuditTrailEntity abstractAuditTrailEntity = auditTrailService
            .createAuditTrail(serviceName, entity);
        /* Set the user activity */
        abstractAuditTrailEntity.setUserActivity(createUserActivity(entity, 
            abstractAuditTrailEntity.getAuditServiceName()));
        addAuditTrail(abstractAuditTrailEntity);
    }
    
    /**
     * 
     * @param result Object
     * @param serviceName String
     * @return  UserActivity
     */
    private UserActivity createUserActivity(Object result, String serviceName) {
        User currentUser = getCurrentUser();
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase(serviceName);
        UserActivity userActivity = new UserActivity();
        userActivity.setActivityDate(new Date());
        userActivity.setActivityUser(currentUser);
        String permissionServiceName = stringConvertor.convertTitleCaseToCamelCase(serviceName);
        Permission permission = findPermission(permissionServiceName);
        userActivity.setPermission(permission);
        userActivity.setDescription(convertedServiceName);
        if (result instanceof AbstractEntity) {
            AbstractEntity abstractEntity = (AbstractEntity) result;
            userActivity.setEntityId(abstractEntity.getId());
        }
        return userActivity;
    }
    
    /**
     * Find and return the current user
     * @return User
     */
    protected User getCurrentUser() {
        /* Get the current logged in user */
        Principal principal = sessionContext.getCallerPrincipal();
        LogManager.getRootLogger().info(new StringBuilder("Principal: ")
            .append(principal.getName())
            .toString());
        
        /* Convert principal to user */
        User currentUser = manageSecurityUserService.convertPrincipalToUser
            (principal.getName());
        return currentUser;
    }
    
    /**
     * Find permission. The activity of a user is tracked according to the permissions
     * the user has in the system.
     * 
     * @param serviceName String
     * @return Permission
     */
    private Permission findPermission(String serviceName) {
        PermissionSearchCriteria permissionSearchCriteria = new 
            PermissionSearchCriteria();
        permissionSearchCriteria.setServiceName(serviceName);
        Collection<Permission> permissions = manageSecurityPermissionService
            .searchPermissions(permissionSearchCriteria);
        if (permissions != null && permissions.size() > 0) {
            return permissions.iterator().next();
        }
        return null;
    }
    
}
