package za.co.jericho.address.service;

import java.util.Collection;
import javax.ejb.Remote;
import za.co.jericho.address.domain.Address;
import za.co.jericho.address.domain.Area;
import za.co.jericho.address.domain.GreaterArea;
import za.co.jericho.address.domain.Suburb;
import za.co.jericho.address.search.AddressSearchCriteria;
import za.co.jericho.address.search.AreaSearchCriteria;
import za.co.jericho.address.search.GreaterAreaSearchCriteria;
import za.co.jericho.address.search.SuburbSearchCriteria;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-21
 */
@Remote
public interface ManageAddressService {
    
    public Address addAddress(Address address);
    
    public Address updateAddress(Address address);
    
    public Address markAddressDeleted(Address address);
    
    public Collection<Address> searchAddresses(AddressSearchCriteria addressSearchCriteria);
    
    public Address findAddress(Object id);
    
    public Collection<Address> findAllAddresses();
    
    public Area addArea(Area area);
    
    public Area updateArea(Area area);
    
    public Area markAreaDeleted(Area area);
    
    public Collection<Area> searchAreas(AreaSearchCriteria parameter);
    
    public Area findArea(Object id);
    
    public Collection<Area> findAllAreas();
    
    public Suburb addSuburb(Suburb suburb);
    
    public Suburb updateSuburb(Suburb suburb);
    
    public Suburb markSuburbDeleted(Suburb suburb);
    
    public Collection<Suburb> searchSuburbs(SuburbSearchCriteria suburbSearchCriteria);
    
    public Suburb findSuburb(Object id);
    
    public Collection<Suburb> findAllSuburbs();
    
    public GreaterArea addGreaterArea(GreaterArea greaterArea);
    
    public GreaterArea updateGreaterArea(GreaterArea greaterArea);
    
    public GreaterArea markGreaterAreaDeleted(GreaterArea greaterArea);
    
    public Collection<GreaterArea> searchGreaterAreas(GreaterAreaSearchCriteria 
        greaterAreaSearchCriteria);
    
    public GreaterArea findGreaterArea(Object id);
    
    public Collection<GreaterArea> findAllGreaterAreas();
    
}
