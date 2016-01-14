package za.co.jericho.documents.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.documents.domain.Document;
import za.co.jericho.documents.search.DocumentSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-08
 */
@Remote
public interface ManageDocumentService {
    
    public Document addDocument(Document document);
    
    public Document updateDocument(Document document);
    
    public Document markDocumentDeleted(Document document);
    
    public List<Document> searchDocuments(DocumentSearchCriteria documentSearchCriteria);
    
    public Document findDocument(Object id);
    
    public List<Document> findAllDocuments();
    
}
