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

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} where noteid = #{noteid} ")
    int update(Note note);

    @Select("SELECT * FROM NOTES WHERE  notetitle = #{notetitle} AND userid = #{userid}")
    Note getNoteByTitle(String notetitle, Integer userid);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid}) ")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insert(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    Integer delete(Integer noteid);
}
