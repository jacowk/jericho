package za.co.jericho.notes.service;

import java.util.List;
import javax.ejb.Remote;
import za.co.jericho.notes.domain.Note;
import za.co.jericho.notes.search.NoteSearchCriteria;
import za.co.jericho.propertyflip.domain.PropertyFlip;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-10-09
 */
@Remote
public interface ManageNoteService {
    
    public Note addNote(PropertyFlip propertyFlip, Note note);
    
    public Note updateNote(Note note);
    
    public Note markNoteDeleted(Note note);
    
    public List<Note> searchNotes(NoteSearchCriteria noteSearchCriteria);
    
}
