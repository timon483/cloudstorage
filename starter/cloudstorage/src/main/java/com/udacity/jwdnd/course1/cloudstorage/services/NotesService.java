package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<Note> getAllNotes(Authentication authentication){
        return notesMapper.getNotesByUser(Integer.valueOf(authentication.getName()));
    }

    public Note getNote(Integer noteId){
        return notesMapper.getNoteById(noteId);
    }

    public int createNote(Note note) {

        return notesMapper.insert(note);
    }

    public int deleteFile(Integer noteId){

        return notesMapper.delete(noteId);
    }

    
}
