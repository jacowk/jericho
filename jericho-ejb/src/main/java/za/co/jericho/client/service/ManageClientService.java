package za.co.jericho.client.service;

import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.client.domain.Purchaser;
import za.co.jericho.client.domain.Seller;
import za.co.jericho.client.search.PurchaserSearchCriteria;
import za.co.jericho.client.search.SellerSearchCriteria;

/**
 * @author Jaco Koekemoer
 * Date: 2015-11-07
 */
@Remote
public interface ManageClientService {
    
    /* Seller services */
    public Seller addSeller(Seller seller);
    
    public Seller updateSeller(Seller seller);
    
    public Seller markSellerDeleted(Seller seller);
            
    public Collection<Seller> searchSellers(SellerSearchCriteria sellerSearchCriteria);
    
    public Seller findSeller(Object id);
    
    public Collection<Seller> findAllSellers();
    
    /* Purchaser services */
    public Purchaser addPurchaser(Purchaser purchaser);
    
    public Purchaser updatePurchaser(Purchaser purchaser);
    
    public Purchaser markPurchaserDeleted(Purchaser purchaser);
            
    public Collection<Purchaser> searchPurchasers(PurchaserSearchCriteria 
        purchaserSearchCriteria);
    
    public Purchaser findPurchaser(Object id);
    
    public Collection<Purchaser> findAllPurchasers();
    
}
