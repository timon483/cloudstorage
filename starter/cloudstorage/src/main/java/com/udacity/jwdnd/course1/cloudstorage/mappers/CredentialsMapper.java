package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid)  VALUES (#{url}, #{username}, #{key}, #{password} ,#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialsByUser(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userid}")
    Credential getCredential(Integer credentialid, Integer userid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} where credentialid = #{credentialid}")
    Integer update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Integer delete(Integer credentialid);

}
