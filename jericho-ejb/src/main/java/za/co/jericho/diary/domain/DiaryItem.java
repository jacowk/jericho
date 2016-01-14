package za.co.jericho.diary.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import za.co.jericho.common.domain.AbstractEntity;
import za.co.jericho.diary.lookup.DiaryItemStatus;
import za.co.jericho.security.domain.User;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-21
 */
@Entity
@Table(name="diary_item", schema="jericho")
public class DiaryItem extends AbstractEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "proposed_followup_date")
    private Date proposedFollowupDate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proposed_followup_user_id")
    private User proposedFollowupUser;
    @Column(name = "comment")
    private String diariseComment;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="diary_id")
    private Diary diary;
    @OneToMany(mappedBy = "diaryItem", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="followup_item_id")
    private List<FollowupItem> followupItems = new ArrayList<>();
    @Column(name="diary_item_status_id")
    private DiaryItemStatus diaryItemStatus;

    public Date getProposedFollowupDate() {
        return proposedFollowupDate;
    }

    public void setProposedFollowupDate(Date proposedFollowupDate) {
        this.proposedFollowupDate = proposedFollowupDate;
    }

    public User getProposedFollowupUser() {
        return proposedFollowupUser;
    }

    public void setProposedFollowupUser(User proposedFollowupUser) {
        this.proposedFollowupUser = proposedFollowupUser;
    }

    public String getDiariseComment() {
        return diariseComment;
    }

    public void setDiariseComment(String diariseComment) {
        this.diariseComment = diariseComment;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public List<FollowupItem> getFollowupItems() {
        return followupItems;
    }

    public void setFollowupItems(List<FollowupItem> followupItems) {
        this.followupItems = followupItems;
    }

    public DiaryItemStatus getDiaryItemStatus() {
        return diaryItemStatus;
    }

    public void setDiaryItemStatus(DiaryItemStatus diaryItemStatus) {
        this.diaryItemStatus = diaryItemStatus;
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