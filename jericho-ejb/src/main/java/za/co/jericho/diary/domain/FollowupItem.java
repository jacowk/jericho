package za.co.jericho.diary.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import za.co.jericho.common.domain.AbstractEntity;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-21
 */
@Entity
@Table(name="followup_item", schema="jericho")
public class FollowupItem extends AbstractEntity {

    @Column(name = "comment")
    private String followupComment;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="diary_item_id")
    private DiaryItem diaryItem;

    public String getFollowupComment() {
        return followupComment;
    }

    public void setFollowupComment(String followupComment) {
        this.followupComment = followupComment;
    }

    public DiaryItem getDiaryItem() {
        return diaryItem;
    }

    public void setDiaryItem(DiaryItem diaryItem) {
        this.diaryItem = diaryItem;
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