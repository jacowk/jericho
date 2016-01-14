package za.co.jericho.contractor.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.contractor.domain.Contractor;
import za.co.jericho.contractor.search.ContractorSearchCriteria;

/**
 * @author Jaco Koekemoer
 * Date: 2015-10-14
 */
@Remote
public interface ManageContractorService {
    
    public Contractor addContractor(Contractor contractor);
    
    public Contractor updateContractor(Contractor contractor);
    
    public Contractor markContractorDeleted(Contractor contractor);
            
    public List<Contractor> searchContractors(ContractorSearchCriteria 
        contractorSearchCriteria);
    
    public Contractor findContractor(Object id);
    
    public List<Contractor> findAllContractors();
    
}
