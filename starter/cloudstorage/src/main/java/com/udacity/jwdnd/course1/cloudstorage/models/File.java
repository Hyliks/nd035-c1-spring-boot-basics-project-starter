package com.udacity.jwdnd.course1.cloudstorage.models;

import java.io.InputStream;

public class File {
    private Integer fileId;
    private String filename;
    private String contentType;
    private String filesize;
    private Integer userId;
    private InputStream  filedata;

    public File(String filename, String contentType, String filesize, InputStream filedata) {
        this.filename = filename;
        this.contentType = contentType;
        this.filesize = filesize;
        this.filedata = filedata;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public InputStream getFiledata() {
        return filedata;
    }

    public void setFiledata(InputStream filedata) {
        this.filedata = filedata;
    }
}