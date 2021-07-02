package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid)  VALUES (#{url}, #{username}, #{key}, #{password} ,#{userid})")
    Integer insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialsByUser(String userid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} where credentialid = #{credentialid}")
    Integer update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Integer delete(Integer credentialid);

}
