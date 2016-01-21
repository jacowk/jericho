package za.co.jericho.address.service;

import za.co.jericho.common.service.*;
import za.co.jericho.address.domain.*;
import java.util.*;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import za.co.jericho.address.search.*;
import za.co.jericho.annotations.AuditTrail;
import za.co.jericho.annotations.SecurityPermission;
import za.co.jericho.annotations.UserActivityMonitor;
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

@Stateless
@Remote(ManageAddressService.class)
@Interceptors({SecurityPermissionInterceptor.class, AuditTrailInterceptor.class,
UserActivityMonitorInterceptor.class})
public class ManageAddressServiceBean extends AbstractServiceBean
implements ManageAddressService {

    @SecurityPermission(serviceName = ServiceName.ADD_ADDRESS)
    @AuditTrail(serviceName = ServiceName.ADD_ADDRESS)
    @Override
    public Address addAddress(Address address) {
        /* Validations */
        /* State validation */
        address.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(address)) {
            getEntityManager().persist(address);
        }
        return address;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_ADDRESS)
    @AuditTrail(serviceName = ServiceName.UPDATE_ADDRESS)
    @Override
    public Address updateAddress(Address address) {
        /* Validations */
        /* State validation */
        address.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(address)) {
            getEntityManager().merge(address);
        }
        return address;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_ADDRESS_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_ADDRESS_DELETED)
    @Override
    public Address markAddressDeleted(Address address) {
        throw new DeleteNotSupportedException("Deleting an address is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_ADDRESSES)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_ADDRESSES)
    @Override
    public Collection<Address> searchAddresses(AddressSearchCriteria addressSearchCriteria) {
        Collection<Address> addresses = new ArrayList<>();
        if (addressSearchCriteria != null) {
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
        }
        else {
            throw new ServiceBeanException("Address search criteria not provided");
        }
        return addresses;
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_ADDRESSES)
    @Override
    public Address findAddress(Object id) {
        return getEntityManager().find(Address.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_ADDRESSES)
    @Override
    public Collection<Address> findAllAddresses() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Address.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SecurityPermission(serviceName = ServiceName.ADD_AREA)
    @AuditTrail(serviceName = ServiceName.ADD_AREA)
    @Override
    public Area addArea(Area area) {
        /* Validations */
        /* State validation */
        area.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(area)) {
            getEntityManager().persist(area);
        }
        return area;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_AREA)
    @AuditTrail(serviceName = ServiceName.UPDATE_AREA)
    @Override
    public Area updateArea(Area area) {
        /* Validations */
        /* State validation */
        area.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(area)) {
            getEntityManager().merge(area);
        }
        return area;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_AREA_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_AREA_DELETED)
    @Override
    public Area markAreaDeleted(Area area) {
        throw new DeleteNotSupportedException("Deleting an area is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_AREAS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_AREAS)
    @Override
    public Collection<Area> searchAreas(AreaSearchCriteria areaSearchCriteria) {
        Collection<Area> areas = new ArrayList<>();
        if (areaSearchCriteria != null) {
            StringConvertor stringConvertor = new StringDataConvertor();
            String name = stringConvertor.convertForDatabaseSearch
                (areaSearchCriteria.getName(), areaSearchCriteria.getSearchType());
            areas = getEntityManager().createNamedQuery("findAreaByName")
                .setParameter("name", name)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Area search criteria not provided");
        }
        return areas;
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_AREAS)
    @Override
    public Area findArea(Object id) {
        return getEntityManager().find(Area.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_AREAS)
    @Override
    public Collection<Area> findAllAreas() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Area.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SecurityPermission(serviceName = ServiceName.ADD_SUBURB)
    @AuditTrail(serviceName = ServiceName.ADD_SUBURB)
    @Override
    public Suburb addSuburb(Suburb suburb) {
        /* Validations */
        /* State validation */
        suburb.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(suburb)) {
            getEntityManager().persist(suburb);
        }
        return suburb;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_SUBURB)
    @AuditTrail(serviceName = ServiceName.UPDATE_SUBURB)
    @Override
    public Suburb updateSuburb(Suburb suburb) {
        /* Validations */
        /* State validation */
        suburb.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(suburb)) {
            getEntityManager().merge(suburb);
        }
        return suburb;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_SUBURB_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_SUBURB_DELETED)
    @Override
    public Suburb markSuburbDeleted(Suburb suburb) {
        throw new DeleteNotSupportedException("Deleting a suburb is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_SUBURBS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_SUBURBS)
    @Override
    public Collection<Suburb> searchSuburbs(SuburbSearchCriteria suburbSearchCriteria) {
        Collection<Suburb> suburbs = new ArrayList<>();
        if (suburbSearchCriteria != null) {
            /* Service logic */
            StringConvertor stringConvertor = new StringDataConvertor();
            StringBuilder searchUsersStringBuilder = new StringBuilder();
            searchUsersStringBuilder.append("SELECT p FROM Suburb p ");
            searchUsersStringBuilder.append("WHERE p.name like :name ");
            if (suburbSearchCriteria.getBoxCode() != null) {
                searchUsersStringBuilder.append("AND p.boxCode like :boxCode ");
            }
            /* Finish this */
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
        }
        else {
            throw new ServiceBeanException("Suburb search criteria not provided");
        }
        return suburbs;
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_SUBURBS)
    @Override
    public Suburb findSuburb(Object id) {
        return getEntityManager().find(Suburb.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_SUBURBS)
    @Override
    public Collection<Suburb> findAllSuburbs() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(Suburb.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SecurityPermission(serviceName = ServiceName.ADD_GREATER_AREA)
    @AuditTrail(serviceName = ServiceName.ADD_GREATER_AREA)
    @Override
    public GreaterArea addGreaterArea(GreaterArea greaterArea) {
        /* Validations */
        /* State validation */
        greaterArea.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeCreate(greaterArea)) {
            getEntityManager().persist(greaterArea);
        }
        return greaterArea;
    }

    @SecurityPermission(serviceName = ServiceName.UPDATE_GREATER_AREA)
    @AuditTrail(serviceName = ServiceName.UPDATE_GREATER_AREA)
    @Override
    public GreaterArea updateGreaterArea(GreaterArea greaterArea) {
        /* Validations */
        /* State validation */
        greaterArea.validate();
        
        /* Service logic */
        EntityValidator entityValidator = new EntityStateValidator();
        if (entityValidator.isValidateEntityBeforeUpdate(greaterArea)) {
            getEntityManager().merge(greaterArea);
        }
        return greaterArea;
    }

    @SecurityPermission(serviceName = ServiceName.MARK_GREATER_AREA_DELETED)
    @UserActivityMonitor(serviceName = ServiceName.MARK_GREATER_AREA_DELETED)
    @Override
    public GreaterArea markGreaterAreaDeleted(GreaterArea greaterArea) {
        throw new DeleteNotSupportedException("Deleting a greater area is not supported");
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_GREATER_AREAS)
    @UserActivityMonitor(serviceName = ServiceName.SEARCH_GREATER_AREAS)
    @Override
    public Collection<GreaterArea> searchGreaterAreas(GreaterAreaSearchCriteria greaterAreaSearchCriteria) {
        Collection<GreaterArea> greaterAreas = new ArrayList<>();
        if (greaterAreaSearchCriteria != null) {
            StringConvertor stringConvertor = new StringDataConvertor();
            String name = stringConvertor.convertForDatabaseSearch
                (greaterAreaSearchCriteria.getName(), greaterAreaSearchCriteria.getSearchType());
            greaterAreas = getEntityManager().createNamedQuery("findGreaterAreaByName")
                .setParameter("name", name)
                .getResultList();
        }
        else {
            throw new ServiceBeanException("Greater Area search criteria not provided");
        }
        return greaterAreas;
    }
    
    @SecurityPermission(serviceName = ServiceName.SEARCH_GREATER_AREAS)
    @Override
    public GreaterArea findGreaterArea(Object id) {
        return getEntityManager().find(GreaterArea.class, id);
    }

    @SecurityPermission(serviceName = ServiceName.SEARCH_GREATER_AREAS)
    @Override
    public Collection<GreaterArea> findAllGreaterAreas() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
            .getCriteriaBuilder().createQuery();
        cq.select(cq.from(GreaterArea.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

}