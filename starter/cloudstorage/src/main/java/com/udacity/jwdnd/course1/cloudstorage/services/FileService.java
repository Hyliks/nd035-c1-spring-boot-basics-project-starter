package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper mapper;

    public FileService(FileMapper mapper) {
        this.mapper = mapper;
    }

    public File addFile(File file, User user) {
        file.setUserId(user.getUserId());
        file.setFileId(this.mapper.insert(file));

        return file;
    }

    public File getFileByName(String filename) {
        return this.mapper.getFileByName(filename);
    }

    public void deleteFile(File file) {
        this.mapper.delete(file);
    }

    public File getFile(Integer fileId, Integer userId) {
        return this.mapper.getFile(fileId,userId);
    }

    public List<File> getFiles(Integer userId) {

        List<File> files = this.mapper.getFilesWithOutBlob(userId);

        for(File file: files) {
            file.setFiledata(null);
        }

        return files;
    }

}
