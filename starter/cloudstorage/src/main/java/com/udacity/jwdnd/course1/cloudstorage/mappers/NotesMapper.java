package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotesByUser(Integer userid);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNoteById(Integer noteid);

    @Update("UPDATE NOTES SET ")

    @Select("SELECT * FROM NOTES WHERE  notetitle = #{notetitle} AND userid = #{userid}")
    Note getNoteByTitle(String notetitle, Integer userid);

    @Insert("INSERT INTO NOTES(noteid, notetitle, notedescription, userid) VALUES(#{noteid}, #{notetitle}, #{notedescription}, #{userid}) ")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insert(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    Integer delete(Integer noteid);
}
