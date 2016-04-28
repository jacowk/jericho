package za.co.jericho.contact.service;

import za.co.jericho.audittrail.service.AbstractAuditTrailService;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.domain.ContactAuditTrail;
import za.co.jericho.common.domain.AbstractAuditTrailEntity;
import za.co.jericho.security.ServiceName;
import za.co.jericho.util.conversion.StringConvertor;
import za.co.jericho.util.conversion.StringDataConvertor;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-03-28
 */
public class ContactAuditTrailService extends AbstractAuditTrailService {
    
    @Override
    public AbstractAuditTrailEntity createAuditTrail(ServiceName serviceName, 
        Object entity) {
        Contact contact = null;
        if (entity instanceof Contact) {
            contact = (Contact) entity;
        }
        else {
            return null;
        }
        ContactAuditTrail contactAuditTrail = new ContactAuditTrail();
        contactAuditTrail.setEntityId(contact.getId());
        contactAuditTrail.setFirstname(contact.getFirstname());
        contactAuditTrail.setSurname(contact.getSurname());
        contactAuditTrail.setHomeTelNumber(contact.getHomeTelNumber());
        contactAuditTrail.setWorkTelNumber(contact.getWorkTelNumber());
        contactAuditTrail.setCellNumber(contact.getCellNumber());
        contactAuditTrail.setFaxNumber(contact.getFaxNumber());
        contactAuditTrail.setWorkEmail(contact.getWorkEmail());
        contactAuditTrail.setPersonalEmail(contact.getPersonalEmail());
        contactAuditTrail.setIdNumber(contact.getIdNumber());
        contactAuditTrail.setPassportNumber(contact.getPassportNumber());
        contactAuditTrail.setMaritalStatus(contact.getMaritalStatus().getId());
        contactAuditTrail.setTaxNumber(contact.getTaxNumber());
        contactAuditTrail.setSaCitizen(contact.getSaCitizen());
        StringConvertor stringConvertor = new StringDataConvertor();
        String convertedServiceName = stringConvertor.convertCamelCaseToTitleCase
            (serviceName.getValue());
        contactAuditTrail.setAuditServiceName(convertedServiceName);
        if (serviceName == ServiceName.ADD_CONTACT) {
            contactAuditTrail.setAuditTrailDate(contact.getCreateDate());
            contactAuditTrail.setAuditTrailUser(contact.getCreatedBy());
        }
        else if (serviceName == ServiceName.UPDATE_CONTACT) {
            contactAuditTrail.setAuditTrailDate(contact.getLastModifyDate());
            contactAuditTrail.setAuditTrailUser(contact.getLastModifiedBy());
        }
        contactAuditTrail.setDeleted(contact.isDeleted());
        return contactAuditTrail;
    }
    
}
