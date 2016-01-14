package za.co.jericho.diary.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.diary.domain.Diary;
import za.co.jericho.diary.domain.DiaryItem;
import za.co.jericho.diary.domain.FollowupItem;
import za.co.jericho.diary.search.DiarySearchCriteria;

/**
 * @author Jaco Koekemoer
 * Date: 2015-10-21
 */
@Remote
public interface ManageDiaryService {
    
    public Diary addDiary(Diary diary);
    
    public Diary updateDiary(Diary diary);
    
    public void diarise(Diary diary, DiaryItem diaryItem);
    
    public DiaryItem updateDiaryItem(DiaryItem diaryItem);
    
    public FollowupItem followup(FollowupItem diaryItem, FollowupItem followupItem);
    
    public FollowupItem updateFollowupItem(FollowupItem followupItem);
    
    public List<DiaryItem> searchDiaryItems(DiarySearchCriteria DiarySearchCriteria);
    
    public Diary selfAllocateDiary(Diary diary);
    
    public DiaryItem addDiaryItem(DiaryItem diaryItem);
    
}
