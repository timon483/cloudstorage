package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {


    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFile(String filename);

    @Delete("DELETE FROM FILES WHERE filename = #{filename}")
    Integer delete(String filename);


}
