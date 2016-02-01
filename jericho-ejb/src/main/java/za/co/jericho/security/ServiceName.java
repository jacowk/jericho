package za.co.jericho.security;

/**
 *
 * @author Jaco Koekemoer
 * 2015-07-20
 */
public enum ServiceName {
    
    NONE("None"),
    
    ADD_ACCOUNT("addAccount"),
    UPDATE_ACCOUNT("updateAccount"),
    MARK_ACCOUNT_DELETED("markAccountDeleted"),
    SEARCH_ACCOUNTS("searchAccounts"),
    
    ADD_ACCOUNT_TYPE("addAccountType"),
    UPDATE_ACCOUNT_TYPE("updateAccountType"),
    MARK_ACCOUNT_TYPE_DELETED("markAccountTypeDeleted"),
    SEARCH_ACCOUNT_TYPES("searchAccountTypes"),
    
    ADD_ADDRESS("addAddress"),
    UPDATE_ADDRESS("updateAddress"),
    MARK_ADDRESS_DELETED("markAddressDeleted"),
    SEARCH_ADDRESSES("searchAddresses"),
    
    ADD_AREA("addArea"),
    UPDATE_AREA("updateArea"),
    MARK_AREA_DELETED("markAreaDeleted"),
    SEARCH_AREAS("searchAreas"),
    
    ADD_ATTORNEY("addAttorney"),
    UPDATE_ATTORNEY("updateAttorney"),
    MARK_ATTORNEY_DELETED("markAttorneyDeleted"),
    SEARCH_ATTORNEYS("searchAttorneys"),
    
    ADD_BANK("addBank"),
    UPDATE_BANK("updateBank"),
    MARK_BANK_DELETED("markBankDeleted"),
    SEARCH_BANKS("searchBanks"),
    
    ADD_CONTACT("addContact"),
    UPDATE_CONTACT("updateContact"),
    MARK_CONTACT_DELETED("markContactDeleted"),
    SEARCH_CONTACTS("searchContacts"),
    
    ADD_CONTRACTOR("addContractor"),
    UPDATE_CONTRACTOR("updateContractor"),
    MARK_CONTRACTOR_DELETED("markContractorDeleted"),
    SEARCH_CONTRACTORS("searchContractors"),
    
    ADD_DIARY("addDiary"),
    UPDATE_DIARY("updateDiary"),
    MARK_DIARY_DELETED("markDiaryDeleted"),
    SEARCH_DIARIES("searchDiaries"),
    
    ADD_DIARY_ITEM("addDiaryItem"),
    UPDATE_DIARY_ITEM("updateDiaryItem"),
    MARK_DIARY_ITEM_DELETED("markDiaryItemDeleted"),
    SEARCH_DIARY_ITEMS("searchDiaryItems"),
    
    ADD_FOLLOWUP_ITEM("addFollowupItem"),
    UPDATE_FOLLOWUP_ITEM("updateFollowupItem"),
    MARK_FOLLOWUP_ITEM_DELETED("markFollowupItemDeleted"),
    SEARCH_FOLLOWUP_ITEMS("searchFollowupItems"),
    
    ADD_DOCUMENT("addDocument"),
    UPDATE_DOCUMENT("updateDocument"),
    MARK_DOCUMENT_DELETED("markDocumentDeleted"),
    SEARCH_DOCUMENTS("searchDocuments"),
    
    ADD_ESTATE_AGENT("addEstateAgent"),
    UPDATE_ESTATE_AGENT("updateEstateAgent"),
    MARK_ESTATE_AGENT_DELETED("markEstateAgentDeleted"),
    SEARCH_ESTATE_AGENTS("searchEstateAgents"),
    
    ADD_USER("addUser"),
    UPDATE_USER("updateUser"),
    MARK_USER_DELETED("markUserDeleted"),
    SEARCH_USERS("searchUsers"),
    
    ADD_PERMISSION("addPermission"),
    UPDATE_PERMISSION("updatePermission"),
    MARK_PERMISSION_DELETED("markPermissionDeleted"),
    SEARCH_PERMISSIONS("searchPermissions"),
    
    ADD_ROLE("addRole"),
    UPDATE_ROLE("updateRole"),
    MARK_ROLE_DELETED("markRoleDeleted"),
    SEARCH_ROLES("searchRoles"),
    
    ADD_SELLER("addSeller"),
    UPDATE_SELLER("updateSeller"),
    MARK_SELLER_DELETED("markSellerDeleted"),
    SEARCH_SELLERS("searchSellers"),
    
    ADD_SUBURB("addSuburb"),
    UPDATE_SUBURB("updateSuburb"),
    MARK_SUBURB_DELETED("markSuburbDeleted"),
    SEARCH_SUBURBS("searchSuburbs"),
    
    ADD_GREATER_AREA("addGreaterArea"),
    UPDATE_GREATER_AREA("updateGreaterArea"),
    MARK_GREATER_AREA_DELETED("markGreaterAreaDeleted"),
    SEARCH_GREATER_AREAS("searchGreaterAreas"),
    
    ADD_PROPERTY("addProperty"),
    UPDATE_PROPERTY("updateProperty"),
    MARK_PROPERTY_DELETED("markPropertyDeleted"),
    SEARCH_PROPERTIES("searchProperties"),
    
    ADD_PROPERTY_FLIP("addPropertyFlip"),
    UPDATE_PROPERTY_FLIP("updatePropertyFlip"),
    MARK_PROPERTY_FLIP_DELETED("markPropertyFlipDeleted"),
    SEARCH_PROPERTY_FLIPS("searchPropertyFlips"),
    
    ADD_PURCHASER("addPurchaser"),
    UPDATE_PURCHASER("updatePurchaser"),
    MARK_PURCHASER_DELETED("markPurchaserDeleted"),
    SEARCH_PURCHASERS("searchPurchasers"),
    
    ADD_MILESTONE("addMilestone"),
    UPDATE_MILESTONE("updateMilestone"),
    MARK_MILESTONE_DELETED("markMilestoneDeleted"),
    SEARCH_MILESTONES("searchMilestones"),
    
    ADD_NOTE("addNote"),
    UPDATE_NOTE("updateNote"),
    MARK_NOTE_DELETED("markNoteDeleted"),
    SEARCH_NOTES("searchNotes"),
    
    ADD_TRANSACTION("addTransaction"),
    UPDATE_TRANSACTION("updateTransaction"),
    MARK_TRANSACTION_DELETED("markTransactionDeleted"),
    SEARCH_TRANSACTIONS("searchTransactions"),
    
    ADD_TRANSACTION_ITEM("addTransactionItem"),
    UPDATE_TRANSACTION_ITEM("updateTransactionItem"),
    MARK_TRANSACTION_ITEM_DELETED("markTransactionItemDeleted"),
    SEARCH_TRANSACTION_ITEMS("searchTransactionItems");
    
    private String value;
    
    ServiceName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
