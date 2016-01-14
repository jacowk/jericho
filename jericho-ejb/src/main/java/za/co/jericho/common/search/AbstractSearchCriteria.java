package za.co.jericho.common.search;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-14
 */
public abstract class AbstractSearchCriteria implements Serializable {
    
    private Long id;
    private boolean deleted = false;
    private SearchType searchType;
    /* This property indicates which user did the search */
    private User serviceUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }
 
    public User getServiceUser() {
        return serviceUser;
    }

    public void setServiceUser(User serviceUser) {
        this.serviceUser = serviceUser;
    }
    
}
