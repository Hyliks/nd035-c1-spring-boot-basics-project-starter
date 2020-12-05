package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper mapper;

    public NoteService(NoteMapper mapper) {
        this.mapper = mapper;
    }

    public Note addNote(Note note, User user) {
        note.setUserId(user.getUserId());
        note.setNoteId(this.mapper.insert(note));
        return note;
    }

    public void editNote(Note note) {
        this.mapper.update(note);
    }

    public void deleteNote(Note note) {
        this.mapper.delete(note);
    }

    public List<Note> getNotes(Integer userId) {
        return this.mapper.getNotes(userId);
    }
}
