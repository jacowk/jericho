package za.co.jericho.seller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import za.co.jericho.client.domain.Seller;
import za.co.jericho.contact.lookup.MaritalStatus;
import za.co.jericho.client.search.SellerSearchCriteria;
import za.co.jericho.client.service.ManageClientService;
import za.co.jericho.contact.domain.Contact;
import za.co.jericho.contact.service.ManageContactService;
import za.co.jericho.propertyflip.PropertyFlipBean;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.security.domain.User;
import za.co.jericho.session.SessionServices;
import za.co.jericho.util.JerichoWebUtil;
import za.co.jericho.util.JsfUtil;
import za.co.jericho.util.PathConstants;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-03-28
 */
@ManagedBean(name = "sellerBean")
@SessionScoped
public class SellerBean implements Serializable {
    
    private PropertyFlip propertyFlip;
    private Seller seller;
    private Contact contact;
    private SellerSearchCriteria sellerSearchCriteria = new SellerSearchCriteria();
    private Collection<Seller> sellers = null;
    private Collection<MaritalStatus> maritalStatusses = new ArrayList<>();
    private long selectedMaritalStatusId;
    private MaritalStatus selectedMaritalStatus;
    private boolean saCitizenValue;
    @EJB
    private ManageClientService manageClientService;
    @EJB
    private ManageContactService manageContactService;
    @ManagedProperty(value = "#{propertyFlipBean}")
    private PropertyFlipBean propertyFlipBean;
    
    public SellerBean() {
        
    }
    
    @PostConstruct
    public void init() {
        LogManager.getRootLogger().info("SellerBean: init");
        propertyFlip = propertyFlipBean.getPropertyFlip();
        LogManager.getRootLogger().info("propertyFlip == null: " + (propertyFlip == null));
        if (seller == null) {
            seller = new Seller();
        }
        if (contact == null) {
            contact = new Contact();
            prepareDataForTesting(); //TODO Temporary step
        }
        if (maritalStatusses == null || maritalStatusses.isEmpty()) {
            maritalStatusses = manageContactService.findAllMaritalStatusses();
        }
        updateSACitizenValue();
        testSessionInvalidation();
    }
    
    private void testSessionInvalidation() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        LogManager.getRootLogger().info("RequestedSessionId: " + request.getRequestedSessionId());
        LogManager.getRootLogger().info("isRequestedSessionIdValid: " + request.isRequestedSessionIdValid());
    }
    
    private void prepareDataForTesting() {
        contact.setFirstname("John");
        contact.setSurname("Doe");
        contact.setHomeTelNumber("011 999 8888");
        contact.setWorkTelNumber("011 888 7777");
        contact.setCellNumber("082 666 5555");
        contact.setWorkTelNumber("011 777 6666");
        contact.setWorkEmail("work@test.co.za");
        contact.setPersonalEmail("personal@test.co.za");
        contact.setIdNumber(7607205162089L);
        contact.setTaxNumber("3232");
    }

    /* Getters and Setters */
    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {    
        this.propertyFlip = propertyFlip;
    }
    
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public SellerSearchCriteria getSellerSearchCriteria() {
        return sellerSearchCriteria;
    }

    public void setSellerSearchCriteria(SellerSearchCriteria sellerSearchCriteria) {
        this.sellerSearchCriteria = sellerSearchCriteria;
    }

    public Collection<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(Collection<Seller> sellers) {
        this.sellers = sellers;
    }

    public Collection<MaritalStatus> getMaritalStatusses() {
        return maritalStatusses;
    }

    public void setMaritalStatusses(Collection<MaritalStatus> maritalStatusses) {
        this.maritalStatusses = maritalStatusses;
    }

    public long getSelectedMaritalStatusId() {
        return selectedMaritalStatusId;
    }

    public void setSelectedMaritalStatusId(long selectedMaritalStatusId) {
        this.selectedMaritalStatusId = selectedMaritalStatusId;
    }

    public MaritalStatus getSelectedMaritalStatus() {
        return selectedMaritalStatus;
    }

    public void setSelectedMaritalStatus(MaritalStatus selectedMaritalStatus) {
        this.selectedMaritalStatus = selectedMaritalStatus;
    }

    public ManageClientService getManageClientService() {
        return manageClientService;
    }

    public void setManageClientService(ManageClientService manageClientService) {
        this.manageClientService = manageClientService;
    }

    public boolean getSaCitizenValue() {
        return saCitizenValue;
    }

    public void setSaCitizenValue(boolean saCitizenValue) {
        this.saCitizenValue = saCitizenValue;
    }

    public PropertyFlipBean getPropertyFlipBean() {
        return propertyFlipBean;
    }

    public void setPropertyFlipBean(PropertyFlipBean propertyFlipBean) {
        this.propertyFlipBean = propertyFlipBean;
    }
    
    /* Service calls */
    public Seller prepareAdd() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SellerBean: prepareAdd")
            .toString());
        seller = new Seller();
        contact = new Contact();
        seller.setContact(contact);
        return seller;
    }
    
    public String addSeller() {
        LogManager.getRootLogger().info("SellerBean: addSeller");
        propertyFlip = propertyFlipBean.getPropertyFlip();
        LogManager.getRootLogger().info("propertyFlip == null: " + (propertyFlip == null));
        try {
            if (seller != null) {
                if (contact != null) {
                    contact.setSaCitizen(saCitizenValue);
                    selectedMaritalStatus = manageContactService
                        .findMaritalStatus(selectedMaritalStatusId);
                    contact.setMaritalStatus(selectedMaritalStatus);
                    seller.setContact(contact);
                }
                seller.setPropertyFlip(propertyFlip);
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                seller.setCreatedBy(currentUser);
                seller.setCreateDate(new Date());
                seller = manageClientService.addSeller(seller);
                updateSACitizenValue();
                if (!JsfUtil.isValidationFailed()) {
                    sellers = null;
                }
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                     .getString("SellerAdded"));
            }
            else {
                JerichoWebUtil.addErrorMessage("Error occured. The Seller was not created.");
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
        return new StringBuilder(PathConstants.MANAGE_PROPERTY_FLIP_PATH.getValue())
            .append(PathConstants.FACES_REDIRECT.getValue())
            .toString();
    }
    
    public void updateSeller() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SellerBean: updateSeller").toString());
        try {
            if (seller != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                seller.setLastModifiedBy(currentUser);
                seller.setLastModifyDate(new Date());
                seller = manageClientService.updateSeller(seller);
                updateSACitizenValue();
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("SellerUpdated"));
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void deleteSeller() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SellerBean: deleteSeller").toString());
        try {
            if (seller != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                seller.setLastModifiedBy(currentUser);
                seller.setLastModifyDate(new Date());
                seller = manageClientService.markSellerDeleted(seller);
                JerichoWebUtil.addSuccessMessage(ResourceBundle
                    .getBundle("/JerichoWebBundle")
                    .getString("SellerDeleted"));
                if (!JsfUtil.isValidationFailed()) {
                    seller = null; // Remove selection
                    seller = null;
                }
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    public void searchSellers() {
        LogManager.getRootLogger().info(new StringBuilder()
            .append("SellerBean: searchSellers").toString());
        try {
            if (sellerSearchCriteria != null) {
                SessionServices sessionServices = new SessionServices();
                User currentUser = sessionServices.getUserFromSession();
                sellerSearchCriteria.setServiceUser(currentUser);
                sellers = manageClientService.searchSellers(sellerSearchCriteria);
            }
        }
        catch (EJBException ex) {
            JerichoWebUtil.addEJBExceptionMessage(ex);
        }
        catch (Exception e) {
            JerichoWebUtil.addGeneralExceptionMessage(e);
        }
    }
    
    /**
     * Update the value for sa citizenship
     */
    private void updateSACitizenValue() {
        if (seller != null && seller.getContact() != null) {
            this.saCitizenValue = seller.getContact().getSaCitizen();
        }
    }
    
}
