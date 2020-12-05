package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid = #{id}")
    Note getNoteById(Integer id);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotes(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(Note note);


    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int delete(Note note);


    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription= #{noteDescription} WHERE noteid= #{noteId}")
    int update(Note note);
}
