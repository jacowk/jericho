package za.co.jericho.documents.search;

import za.co.jericho.common.search.AbstractSearchCriteria;
import za.co.jericho.documents.lookup.DocumentType;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
public class DocumentSearchCriteria extends AbstractSearchCriteria {
    
    private DocumentType documentType;
    private String description;

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}