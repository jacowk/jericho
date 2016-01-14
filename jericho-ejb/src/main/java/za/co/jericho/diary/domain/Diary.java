package za.co.jericho.diary.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.propertyflip.domain.PropertyFlip;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-21
 */
@Entity
@Table(name="diary", schema = "jericho")
public class Diary extends AbstractEntity {

    @OneToMany(mappedBy = "diary", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="diary_item_id")
    private List<DiaryItem> diaryItems;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="property_flip_id")
    private PropertyFlip propertyflip;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "allocated_user_id")
    private User allocatedUser;
    //TODO: Status

    public List<DiaryItem> getDiaryItems() {
        return diaryItems;
    }

    public void setDiaryItems(List<DiaryItem> diaryItems) {
        this.diaryItems = diaryItems;
    }

    public PropertyFlip getPropertyflip() {
        return propertyflip;
    }

    public void setPropertyflip(PropertyFlip propertyflip) {
        this.propertyflip = propertyflip;
    }

    public User getAllocatedUser() {
        return allocatedUser;
    }

    public void setAllocatedUser(User allocatedUser) {
        this.allocatedUser = allocatedUser;
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
        return stringBuilder.toString();
    }
    
}