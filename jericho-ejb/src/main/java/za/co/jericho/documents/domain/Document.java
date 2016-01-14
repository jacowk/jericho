package za.co.jericho.documents.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.documents.lookup.DocumentType;
import za.co.jericho.propertyflip.domain.PropertyFlip;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-17
 */
@Entity
@Table(name="document", schema="jericho")
public class Document extends AbstractEntity {

    @Column(name = "description", nullable = false)
    private String description;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "property_flip_id")
    private PropertyFlip propertyFlip;
    @Column(name = "document_type")
    private DocumentType documentType; //TODO
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "uploaded_document")
    private char[] uploadedDocument; //TODO Fix the type

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyFlip getPropertyFlip() {
        return propertyFlip;
    }

    public void setPropertyFlip(PropertyFlip propertyFlip) {
        this.propertyFlip = propertyFlip;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public char[] getUploadedDocument() {
        return uploadedDocument;
    }

    public void setUploadedDocument(char[] uploadedDocument) {
        this.uploadedDocument = uploadedDocument;
    }

    @Override
    public void validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getId() != null) {
            stringBuilder.append("|ID: ");
            stringBuilder.append(getId());
        }
        stringBuilder.append("|Deleted: ");
        stringBuilder.append(Boolean.toString(super.isDeleted()));
        return stringBuilder.toString();
    }
    
}