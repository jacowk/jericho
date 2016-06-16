package za.co.jericho.util;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-04-13
 */
public enum PathConstants {
    
    HOME("/jericho/index.xhtml"),
    LOGIN("/login.xhtml"),
    LOGIN_ERROR("/loginerror.xhtml"),
    LIST_USERS("/jericho/security/user/list-users.xhtml"),
    LIST_ROLES("/jericho/security/role/list-roles.xhtml"),
    LIST_PERMISSIONS("/jericho/security/permission/list-permissions.xhtml"),
    LIST_AUDIT_ACTIVITY("/jericho/auditactivity/list-audit-activity.xhtml"),
    LIST_PROPERTIES("/jericho/property/list-properties.xhtml"),
    LIST_SELLERS("/jericho/seller/list-sellers.xhtml"),
    LIST_PURCHASERS("/jericho/property/list-purchasers.xhtml"),
    LIST_AREAS("/jericho/address/list-areas.xhtml"),
    LIST_GREATER_AREAS("/jericho/address/list-greater-areas.xhtml"),
    LIST_SUBBURBS("/jericho/address/list-suburbs.xhtml"),
    LIST_ATTORNEYS("/jericho/attorney/list-attorneys.xhtml"),
    LIST_BANKS("/jericho/bank/list-banks.xhtml"),
    LIST_CONTRACTORS("/jericho/contractor/list-contractors.xhtml"),
    LIST_ESTATE_AGENTS("/jericho/estateagent/list-estate-agents.xhtml"),
    LIST_CONTACTS("/jericho/contact/list-contacts.xhtml"),
    MANAGE_PROPERTY_FLIP_PATH("/jericho/propertyflip/manage-property-flip.xhtml"),
    UPDATE_PROPERTY_FLIP_PATH("/jericho/propertyflip/update-property-flip.xhtml"),
    ADD_SELLER_PATH("/jericho/seller/add-seller.xhtml"),
    SELECT_SELLER_PATH("/jericho/propertyflip/select-seller.xhtml"),
    SELECT_PURCHASER_PATH("/jericho/propertyflip/select-purchaser.xhtml"),
    FACES_REDIRECT("?faces-redirect=true");

    PathConstants(String value) {
        this.value = value;
    }
    
    private String value;
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
