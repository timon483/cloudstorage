package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;
    private UserMapper userMapper;

    public NotesService(NotesMapper notesMapper, UserMapper userMapper) {
        this.notesMapper = notesMapper;
        this.userMapper = userMapper;
    }

    public List<Note> getAllNotes(Authentication authentication){
        User user = userMapper.getUser(authentication.getName());
        return notesMapper.getNotesByUser(user.getUserid());
    }

    public Note getNote(Integer noteId){
        return notesMapper.getNoteById(noteId);
    }

    public int createOrUpdateNote(Note note) {

        if (note.getNoteid() == null) {

            return notesMapper.insert(new Note(null, note.getNotetitle(), note.getNotedescription(), note.getUserid()));
        } else {
            return notesMapper.update(note);
        }

    }

    public int deleteFile(Integer noteId){

        return notesMapper.delete(noteId);
    }

    
}
