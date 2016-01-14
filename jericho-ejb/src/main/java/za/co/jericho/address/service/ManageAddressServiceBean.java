package za.co.jericho.address.service;

import za.co.jericho.common.service.*;
import za.co.jericho.address.domain.*;
import java.util.*;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import za.co.jericho.address.search.*;
import za.co.jericho.audit.AuditActivityFactory;
import za.co.jericho.audit.lookup.AuditActivityType;
import za.co.jericho.audit.lookup.EntityName;
import za.co.jericho.exception.DeleteNotSupportedException;
import za.co.jericho.exception.ServiceBeanException;
import za.co.jericho.security.ServiceName;
import za.co.jericho.security.service.permissioncheck.PermissionChecker;
import za.co.jericho.security.service.permissioncheck.UserPermissionChecker;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;
import za.co.jericho.util.validation.EntityStateValidator;
import za.co.jericho.util.validation.EntityValidator;

@Stateless
@Remote(ManageAddressService.class)
public class ManageAddressServiceBean extends AbstractServiceBean
implements ManageAddressService {

    @Override
    public Address addAddress(Address address) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(address.getCreatedBy(), ServiceName.ADD_ADDRESS.getValue());
        
        /* Validations */
        /* State validation */
        address.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(address)) {
            getEntityManager().persist(address);
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.ADDRESS, //EntityName entityName
            ServiceName.ADD_ADDRESS.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            address.toString(), //String description
            address.getCreatedBy())); //User currentUser
        return address;
    }

    @Override
    public Address updateAddress(Address address) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(address.getLastModifiedBy(), ServiceName.UPDATE_ADDRESS.getValue());
        
        /* Validations */
        /* State validation */
        address.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(address)) {
            getEntityManager().merge(address);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.ADDRESS, //EntityName entityName
            ServiceName.UPDATE_ADDRESS.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            address.toString(), //String description
            address.getLastModifiedBy())); //User currentUser
        return address;
    }

    @Override
    public Address markAddressDeleted(Address address) {
        throw new DeleteNotSupportedException("Deleting an address is not supported");
    }

    @Override
    public Collection<Address> searchAddresses(AddressSearchCriteria addressSearchCriteria) {
        Collection<Address> addresses = new ArrayList<>();
        if (addressSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(addressSearchCriteria.getServiceUser(), ServiceName.SEARCH_ADDRESSES.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT a FROM Address a ");
            searchUsersStringBuilder.append("WHERE a.addressLine1 like :addressLine1 ");
            searchUsersStringBuilder.append("AND a.addressLine2 like :addressLine2 ");
            searchUsersStringBuilder.append("AND a.addressLine3 like :addressLine3 ");
            searchUsersStringBuilder.append("AND a.addressLine4 like :addressLine4 ");
            searchUsersStringBuilder.append("AND a.addressLine5 like :addressLine5 ");
            searchUsersStringBuilder.append("AND a.suburb = :suburb ");
            searchUsersStringBuilder.append("AND a.suburb.area = :area ");
            searchUsersStringBuilder.append("AND a.greaterArea = :greaterArea;");
            
            String addressLine1 = stringConvertor.convertForDatabaseSearch(
                addressSearchCriteria.getAddressLine1(), addressSearchCriteria.getSearchType());
            String addressLine2 = stringConvertor.convertForDatabaseSearch(
                addressSearchCriteria.getAddressLine2(), addressSearchCriteria.getSearchType());
            String addressLine3 = stringConvertor.convertForDatabaseSearch(
                addressSearchCriteria.getAddressLine3(), addressSearchCriteria.getSearchType());
            String addressLine4 = stringConvertor.convertForDatabaseSearch(
                addressSearchCriteria.getAddressLine4(), addressSearchCriteria.getSearchType());
            String addressLine5 = stringConvertor.convertForDatabaseSearch(
                addressSearchCriteria.getAddressLine5(), addressSearchCriteria.getSearchType());
            
            addresses = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("addressLine1", addressLine1)
                .setParameter("addressLine2", addressLine2)
                .setParameter("addressLine3", addressLine3)
                .setParameter("addressLine4", addressLine4)
                .setParameter("addressLine5", addressLine5)
                .setParameter("suburb", addressSearchCriteria.getSuburb())
                .setParameter("area", addressSearchCriteria.getArea())
                .setParameter("greaterArea", addressSearchCriteria.getGreaterArea())
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.ADDRESS, //String entityName
                ServiceName.SEARCH_ADDRESSES.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                addressSearchCriteria.toString(), //String description
                addressSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Address search criteria not provided");
        }
        return addresses;
    }
    
    @Override
    public Address findAddress(Object id) {
        return getEntityManager().find(Address.class, id);
    }

    @Override
    public Collection<Address> findAllAddresses() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Address.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Area addArea(Area area) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(area.getCreatedBy(), ServiceName.ADD_AREA.getValue());
        
        /* Validations */
        /* State validation */
        area.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(area)) {
            getEntityManager().persist(area);
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.AREA, //EntityName entityName
            ServiceName.ADD_AREA.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            area.toString(), //String description
            area.getCreatedBy())); //User currentUser
        return area;
    }

    @Override
    public Area updateArea(Area area) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(area.getLastModifiedBy(), ServiceName.UPDATE_AREA.getValue());
        
        /* Validations */
        /* State validation */
        area.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(area)) {
            getEntityManager().merge(area);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.AREA, //EntityName entityName
            ServiceName.UPDATE_AREA.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            area.toString(), //String description
            area.getLastModifiedBy())); //User currentUser
        return area;
    }

    @Override
    public Area markAreaDeleted(Area area) {
        throw new DeleteNotSupportedException("Deleting an area is not supported");
    }

    @Override
    public Collection<Area> searchAreas(AreaSearchCriteria areaSearchCriteria) {
        Collection<Area> areas = new ArrayList<>();
        if (areaSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(areaSearchCriteria.getServiceUser(), ServiceName.SEARCH_AREAS.getValue());
            StringConvertor stringConvertor = new StringDataConvertor();
            String name = stringConvertor.convertForDatabaseSearch
                (areaSearchCriteria.getName(), areaSearchCriteria.getSearchType());
            areas = getEntityManager().createNamedQuery("findAreaByName")
                .setParameter("name", name)
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.AREA, //String entityName
                ServiceName.SEARCH_AREAS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                areaSearchCriteria.toString(), //String description
                areaSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Area search criteria not provided");
        }
        return areas;
    }
    
    @Override
    public Area findArea(Object id) {
        return getEntityManager().find(Area.class, id);
    }

    @Override
    public Collection<Area> findAllAreas() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Area.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Suburb addSuburb(Suburb suburb) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(suburb.getCreatedBy(), ServiceName.ADD_SUBURB.getValue());
        
        /* Validations */
        /* State validation */
        suburb.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(suburb)) {
            getEntityManager().persist(suburb);
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.SUBURB, //EntityName entityName
            ServiceName.ADD_SUBURB.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            suburb.toString(), //String description
            suburb.getCreatedBy())); //User currentUser
        return suburb;
    }

    @Override
    public Suburb updateSuburb(Suburb suburb) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(suburb.getLastModifiedBy(), ServiceName.UPDATE_SUBURB.getValue());
        
        /* Validations */
        /* State validation */
        suburb.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(suburb)) {
            getEntityManager().merge(suburb);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.SUBURB, //EntityName entityName
            ServiceName.UPDATE_SUBURB.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            suburb.toString(), //String description
            suburb.getLastModifiedBy())); //User currentUser
        return suburb;
    }

    @Override
    public Suburb markSuburbDeleted(Suburb suburb) {
        throw new DeleteNotSupportedException("Deleting a suburb is not supported");
    }

    @Override
    public Collection<Suburb> searchSuburbs(SuburbSearchCriteria suburbSearchCriteria) {
        Collection<Suburb> suburbs = new ArrayList<>();
        if (suburbSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(suburbSearchCriteria.getServiceUser(), ServiceName.SEARCH_SUBURBS.getValue());
            
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT p FROM Suburb p ");
            searchUsersStringBuilder.append("WHERE p.name like :name ");
            searchUsersStringBuilder.append("AND p.boxCode like :boxCode ");
            searchUsersStringBuilder.append("AND p.streetCode like :streetCode ");
            String name = stringConvertor.convertForDatabaseSearch(suburbSearchCriteria.getName(), 
                suburbSearchCriteria.getSearchType()); 
            String boxCode = suburbSearchCriteria.getBoxCode();            
            String streetCode = suburbSearchCriteria.getStreetCode();            
            suburbs = getEntityManager().createQuery(searchUsersStringBuilder.toString())
                .setParameter("name", name)
                .setParameter("boxCode", boxCode)
                .setParameter("streetCode", streetCode)
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.SUBURB, //String entityName
                ServiceName.SEARCH_SUBURBS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                suburbSearchCriteria.toString(), //String description
                suburbSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Suburb search criteria not provided");
        }
        return suburbs;
    }
    
    @Override
    public Suburb findSuburb(Object id) {
        return getEntityManager().find(Suburb.class, id);
    }

    @Override
    public Collection<Suburb> findAllSuburbs() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Suburb.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public GreaterArea addGreaterArea(GreaterArea greaterArea) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(greaterArea.getCreatedBy(), ServiceName.ADD_GREATER_AREA.getValue());
        
        /* Validations */
        /* State validation */
        greaterArea.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(greaterArea)) {
            getEntityManager().persist(greaterArea);
        }
        /* Audit Activity Logging */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.GREATER_AREA, //EntityName entityName
            ServiceName.ADD_GREATER_AREA.getValue(), //String serviceName
            AuditActivityType.ADD, //AuditActivityType activityType
            greaterArea.toString(), //String description
            greaterArea.getCreatedBy())); //User currentUser
        return greaterArea;
    }

    @Override
    public GreaterArea updateGreaterArea(GreaterArea greaterArea) {
        /* Check permissions */
        PermissionChecker permissionChecker = new UserPermissionChecker();
        permissionChecker.check(greaterArea.getLastModifiedBy(), ServiceName.UPDATE_GREATER_AREA.getValue());
        
        /* Validations */
        /* State validation */
        greaterArea.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(greaterArea)) {
            getEntityManager().merge(greaterArea);
        }
        /* Audit Activity Logging */
        /* Long entityId, String entityName, String serviceBeanName, String serviceName, String activityType, String description, User currentUser */
        AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
        manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
            (EntityName.GREATER_AREA, //EntityName entityName
            ServiceName.UPDATE_GREATER_AREA.getValue(), //String serviceName
            AuditActivityType.UPDATE, //AuditActivityType auditActivityType
            greaterArea.toString(), //String description
            greaterArea.getLastModifiedBy())); //User currentUser
        return greaterArea;
    }

    @Override
    public GreaterArea markGreaterAreaDeleted(GreaterArea greaterArea) {
        throw new DeleteNotSupportedException("Deleting a greater area is not supported");
    }

    @Override
    public Collection<GreaterArea> searchGreaterAreas(GreaterAreaSearchCriteria greaterAreaSearchCriteria) {
        Collection<GreaterArea> greaterAreas = new ArrayList<>();
        if (greaterAreaSearchCriteria != null) {
            /* Check permissions */
            PermissionChecker permissionChecker = new UserPermissionChecker();
            permissionChecker.check(greaterAreaSearchCriteria.getServiceUser(), ServiceName.SEARCH_GREATER_AREAS.getValue());
            StringConvertor stringConvertor = new StringDataConvertor();
            String name = stringConvertor.convertForDatabaseSearch
                (greaterAreaSearchCriteria.getName(), greaterAreaSearchCriteria.getSearchType());
            greaterAreas = getEntityManager().createNamedQuery("findGreaterAreaByName")
                .setParameter("name", name)
                .getResultList();
            
            /* Audit Activity Logging */
            AuditActivityFactory auditActivityFactory = AuditActivityFactory.getInstance();
            manageAuditActivityService.addAuditActivity(auditActivityFactory.createAuditActivity
                (EntityName.GREATER_AREA, //String entityName
                ServiceName.SEARCH_GREATER_AREAS.getValue(), //String serviceName
                AuditActivityType.SEARCH, //String activityType
                greaterAreaSearchCriteria.toString(), //String description
                greaterAreaSearchCriteria.getServiceUser())); //User currentUser
        }
        else {
            throw new ServiceBeanException("Greater Area search criteria not provided");
        }
        return greaterAreas;
    }
    
    @Override
    public GreaterArea findGreaterArea(Object id) {
        return getEntityManager().find(GreaterArea.class, id);
    }

    @Override
    public Collection<GreaterArea> findAllGreaterAreas() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(GreaterArea.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}