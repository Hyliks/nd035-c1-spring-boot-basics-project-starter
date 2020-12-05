package com.udacity.jwdnd.course1.cloudstorage.models;

public class File {
    private Integer fileId;
    private String filename;
    private String contentType;
    private String filesize;
    private Integer userId;
    private byte[]  filedata;

    public File(String filename, String contentType, String filesize, byte[] filedata) {
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

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}